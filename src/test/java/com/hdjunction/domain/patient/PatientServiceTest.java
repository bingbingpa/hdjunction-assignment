package com.hdjunction.domain.patient;

import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.hospital.HospitalRepository;
import com.hdjunction.domain.patient.dto.request.CreatePatientRequest;
import com.hdjunction.domain.patient.dto.request.UpdatePatientRequest;
import com.hdjunction.domain.patient.dto.response.PatientResponse;
import com.hdjunction.domain.visit.Visit;
import com.hdjunction.domain.visit.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class PatientServiceTest {

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
    private final List<Patient> patients = new ArrayList<>();
    private final List<Visit> visits = new ArrayList<>();

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

        for (Visit.VisitBuilder visit : FakeData.getVisits(20)) {
            Visit build = visit.hospital(hospital).patient(patients.get(0)).build();
            visits.add(build);
            visitRepository.save(build);
        }
    }

    @Test
    @DisplayName("내원정보를 포함한 환자 정보 조회")
    void findById() {
        Long findId = patients.get(0).getId();
        PatientResponse response = patientService.findById(findId);

        assertAll(
                () -> assertThat(response.getId()).isEqualTo(findId),
                () -> assertThat(response.getVisits().size()).isEqualTo(visits.size())
        );
    }

    @Test
    void save() {
        CreatePatientRequest createPatientRequest = getPatientResponse();
        Long savedId = patientService.save(createPatientRequest);

        Patient patient = patientRepository.findById(savedId).orElseThrow(NotFoundPatientException::new);

        assertAll(
                () -> assertThat(patient.getBirthday()).isEqualTo("2020-10-10"),
                () -> assertThat(patient.getPhone()).isEqualTo("010-1234-5678"),
                () -> assertThat(patient.getRegistrationNo()).isEqualTo(LocalDate.now().getYear() + "000" + patient.getId())
        );
    }

    @Test
    void update() {
        CreatePatientRequest createPatientRequest = getPatientResponse();
        Long savedId = patientService.save(createPatientRequest);

        UpdatePatientRequest updateRequest = UpdatePatientRequest.builder()
                .name("철수")
                .gender("F")
                .birthDay("19990101")
                .phone("01099998888")
                .build();

        patientService.update(savedId, updateRequest);

        Patient patient = patientRepository.findById(savedId).orElseThrow(NotFoundPatientException::new);

        assertAll(
                () -> assertThat(patient.getName()).isEqualTo("철수"),
                () -> assertThat(patient.getGender()).isEqualTo("여"),
                () -> assertThat(patient.getBirthday()).isEqualTo("1999-01-01"),
                () -> assertThat(patient.getPhone()).isEqualTo("010-9999-8888"),
                () -> assertThat(patient.getRegistrationNo()).isEqualTo(LocalDate.now().getYear() + "000" + savedId)
        );

    }

    @Test
    void delete() {
        Long id = patients.get(0).getId();

        patientService.delete(id);

        assertThatThrownBy(() -> patientService.findById(id))
                .isInstanceOf(NotFoundPatientException.class);
    }

    private CreatePatientRequest getPatientResponse() {
        return CreatePatientRequest.builder()
                .hospitalId(hospital.getId())
                .name("홍길동")
                .gender(Code.MALE.getCode())
                .birthDay("20201010")
                .phone("01012345678")
                .build();
    }
}