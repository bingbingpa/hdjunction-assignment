package com.hdjunction.domain.patient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneTest {

    @Test
    @DisplayName("전화번호에 - 를 포함해서 생성한다.")
    void create() {
        assertThat(Phone.from("01012345678").getPhone()).isEqualTo("010-1234-5678");
    }

    @ParameterizedTest
    @CsvSource(value = {"abcdef", "01012345123456", "99912312312"})
    @DisplayName("유효하지 않은 전화번호 검증")
    void validation(String value) {
        assertThatThrownBy(() -> Phone.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

}