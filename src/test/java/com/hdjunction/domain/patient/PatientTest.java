package com.hdjunction.domain.patient;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {

    @ParameterizedTest
    @CsvSource(value = {"5:00005", "85:00085", "123:00123", "1234: 01234", "12349:12349", "1234567:1234567"}, delimiter = ':')
    void create_registrationNo(String input, String expected) {
        assertThat(Patient.generateRegistrationNumber(input)).isEqualTo(LocalDate.now().getYear() + expected);
    }
}