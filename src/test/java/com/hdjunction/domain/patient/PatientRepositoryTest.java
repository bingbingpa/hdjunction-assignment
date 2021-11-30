package com.hdjunction.domain.patient;

import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.hospital.HospitalRepository;
import com.hdjunction.domain.patient.dto.response.PatientPageResponse;
import com.hdjunction.domain.visit.Visit;
import com.hdjunction.domain.visit.VisitRepository;
import com.hdjunction.global.config.JpaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(JpaConfig.class)
@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private VisitRepository visitRepository;

    private Hospital hospital;
    private final List<Patient> patients = new ArrayList<>();

    @BeforeEach
    void setup() {
        hospital = FakeData.getHospital();
        hospitalRepository.save(hospital);

        for (Patient.PatientBuilder patient : FakeData.getPatients(20)) {
            Patient build = patient.hospital(hospital).build();
            patients.add(build);
            patientRepository.save(build);
        }


        List<Visit.VisitBuilder> visitBuilders = FakeData.getVisits(30);
        int size = visitBuilders.size();
        for (int i = 0; i < size; i++) {
            Visit build = visitBuilders.get(i).hospital(hospital).patient(patients.get(i / 5)).build();
            visitRepository.save(build);
        }
    }

    @Test
    @DisplayName("특정 병원의 환자번호중 가장 큰값을 조회한다.")
    void findMaxPatientIdByHospitalId() {
        Long maxId = patientRepository.findMaxPatientIdByHospitalId(hospital.getId());

        assertThat(maxId).isEqualTo(patients.get(patients.size()-1).getId());
    }

    @Test
    @DisplayName("동적 검색 페이징 조회")
    void dynamic_search() {
        Page<PatientPageResponse> search = patientRepository.search(PatientSearchType.DATE_OF_BIRTH, "20200101", PageRequest.of(1, 5));

        assertAll(
                () -> assertThat(search.getContent().get(0).getId()).isEqualTo(patients.get(0).getId()),
                () -> assertThat(search.getPageable().getPageNumber()).isEqualTo(1),
                () -> assertThat(search.getTotalPages()).isEqualTo(patients.size() / 5)
        );
    }
}