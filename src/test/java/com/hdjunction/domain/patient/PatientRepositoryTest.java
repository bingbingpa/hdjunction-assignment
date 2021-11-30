package com.hdjunction.domain.patient;

import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.hospital.HospitalRepository;
import com.hdjunction.global.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaConfig.class)
@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Test
    @DisplayName("특정 병원의 환자번호중 가장 큰값을 조회한다.")
    void findMaxPatientIdByHospitalId() {
        Hospital hospital = FakeData.getHospital();
        hospitalRepository.save(hospital);

        int maxValue = 10;

        for (Patient.PatientBuilder patient : FakeData.getPatients(maxValue)) {
            Patient build = patient.hospital(hospital).build();
            patientRepository.save(build);
        }

        Long maxId = patientRepository.findMaxPatientIdByHospitalId(hospital.getId());

        assertThat(maxId).isEqualTo(maxValue);
    }
}