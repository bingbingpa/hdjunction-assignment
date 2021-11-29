package com.hdjunction.domain.patient;

import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.hospital.HospitalRepository;
import com.hdjunction.global.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaConfig.class)
@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Test
    @DisplayName("특정 병원의 환자등록번호중 가장 큰값을 조회한다.")
    void findMaxPatientIdByHospitalId() {
        Hospital hospital = Hospital.builder()
                .name("병원")
                .directorName("홍길동")
                .ykiho("1234567")
                .build();
        hospitalRepository.save(hospital);

        int maxValue = 10;

        IntStream.range(0, maxValue)
                .forEach(i -> patientRepository.save(
                        Patient.builder()
                                .hospital(hospital)
                                .registrationNo(String.valueOf(i))
                                .name("test" + i)
                                .gender(Code.MALE)
                                .build()
                ));

        Long maxId = patientRepository.findMaxPatientIdByHospitalId(hospital.getId());

        assertThat(maxId).isEqualTo(maxValue);
    }
}