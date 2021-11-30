package com.hdjunction.domain.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.hospital.HospitalRepository;
import com.hdjunction.domain.patient.dto.request.CreatePatientRequest;
import com.hdjunction.domain.patient.dto.request.UpdatePatientRequest;
import com.hdjunction.domain.visit.Visit;
import com.hdjunction.domain.visit.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.hdjunction.domain.patient.PatientController.API_PATIENTS_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    private final List<Patient> patients = new ArrayList<>();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private EntityManager em;
    private Hospital hospital;

    @BeforeEach
    void setup() {
        hospital = FakeData.getHospital();
        hospitalRepository.save(hospital);

        for (Patient.PatientBuilder patient : FakeData.getPatients(20)) {
            Patient build = patient.hospital(hospital).build();
            patients.add(build);
            patientRepository.save(build);
        }

        em.flush();
        em.clear();

        List<Visit.VisitBuilder> visitBuilders = FakeData.getVisits(30);
        int size = visitBuilders.size();
        for (int i = 0; i < size; i++) {
            Visit build = visitBuilders.get(i).hospital(hospital).patient(patients.get(i / 5)).build();
            visitRepository.save(build);
        }
    }

    @Test
    void findById() throws Exception {
        Patient patient = patients.get(0);

        mockMvc.perform(get(API_PATIENTS_PATH + "/{id}", patient.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(("payload.name")).value(patient.getName()))
                .andExpect(jsonPath(("payload.gender")).value(patient.getGender()))
                .andExpect(jsonPath(("payload.birthDay")).value(patient.getBirthday()));
    }

    @Test
    void search() throws Exception {
        int page = 1;
        int size = 10;

        mockMvc.perform(get(API_PATIENTS_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("type", PatientSearchType.BIRTHDAY.toString())
                        .param("value", "20200101")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath(("payload.content")).exists())
                .andExpect(jsonPath(("payload.pageable")).exists())
                .andExpect(jsonPath(("payload.pageable.pageNumber")).value(page))
                .andExpect(jsonPath(("payload.pageable.pageSize")).value(size));
    }

    @Test
    void save() throws Exception {
        CreatePatientRequest createPatientRequest = CreatePatientRequest.builder()
                .hospitalId(hospital.getId())
                .name("테스트")
                .gender("F")
                .birthDay("20061001")
                .phone("01099991234")
                .build();

        mockMvc.perform(post(API_PATIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPatientRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        Patient patient = patients.get(0);
        UpdatePatientRequest updatePatientRequest = UpdatePatientRequest.builder()
                .name("유저")
                .gender("M")
                .birthDay("19991010")
                .phone("01099991234")
                .build();

        mockMvc.perform(put(API_PATIENTS_PATH + "/{id}", patient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePatientRequest)))
                .andExpect(status().isNoContent());

        Patient saved = patientRepository.findById(patient.getId()).orElseThrow(NotFoundPatientException::new);

        assertAll(
                () -> assertThat(saved.getName()).isEqualTo(updatePatientRequest.getName()),
                () -> assertThat(saved.getGender()).isEqualTo(Code.MALE.getName()),
                () -> assertThat(saved.getBirthday()).isEqualTo("1999-10-10")
        );
    }

    @Test
    void deletePatient() throws Exception {
        Long id = patients.get(0).getId();

        mockMvc.perform(delete(API_PATIENTS_PATH + "/{id}", id))
                .andExpect(status().isNoContent());

        assertThatThrownBy(() -> patientService.findById(id))
                .isInstanceOf(NotFoundPatientException.class);
    }

    @Test
    @DisplayName("request dto 객체의 @Valid 동작 확인")
    void dto_validation() throws Exception {
        CreatePatientRequest createPatientRequest = CreatePatientRequest.builder()
                .hospitalId(hospital.getId())
                .build();

        mockMvc.perform(post(API_PATIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPatientRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("status")).value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath(("errorMessage")).exists());
    }

    @Test
    @DisplayName("request dto 객체의 @Pattern 동작 확인")
    void dto_validation_pattern() throws Exception {
        CreatePatientRequest createPatientRequest = CreatePatientRequest.builder()
                .hospitalId(hospital.getId())
                .name("테스트")
                .gender("F")
                .birthDay("122223004049fjvn")
                .phone("01099991234")
                .build();

        mockMvc.perform(post(API_PATIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPatientRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("status")).value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath(("errorMessage")).exists());
    }

    @Test
    @DisplayName("없는 아이디를 조회할 경우")
    void not_found_patient() throws Exception {
        mockMvc.perform(get(API_PATIENTS_PATH + "/{id}", 123456)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("status")).value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath(("errorMessage")).exists());
    }
}