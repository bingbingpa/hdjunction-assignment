package com.hdjunction.domain.patient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {

    @ParameterizedTest
    @CsvSource(value = {"5:00006", "85:00086", "123:00124", "1234: 01235", "12349:12350", "1234567:1234568"}, delimiter = ':')
    @DisplayName("입력값에 5자리 0 패딩을 적용해서 1을 더한값을 리턴한다.")
    void create_registrationNo(String input, String expected) {
        Patient patient = new Patient();
        patient.generateRegistrationNumber(input);
        assertThat(patient.getRegistrationNo()).isEqualTo(LocalDate.now().getYear() + expected);
    }
}