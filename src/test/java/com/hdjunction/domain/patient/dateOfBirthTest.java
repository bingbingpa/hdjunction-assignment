package com.hdjunction.domain.patient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class dateOfBirthTest {

    @Test
    @DisplayName("yyyymmdd 형식을 yyyy-mm-dd 형태로 변환하여 생성한다.")
    void create() {
        assertThat(DateOfBirth.from("20200102").getDateOfBirth()).isEqualTo("2020-01-02");
    }

    @ParameterizedTest
    @CsvSource(value = {"2020", "10101303", "11111111", "20201301"})
    @DisplayName("유효하지 않은 생년월일 검증")
    void validation(String value) {
        assertThatThrownBy(() -> DateOfBirth.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}