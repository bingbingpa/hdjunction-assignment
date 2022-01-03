package com.hdjunction.domain.code

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class CodeTest {

    @Test
    @DisplayName("코드 찾기")
    fun `find code`() {
        assertThat(Code.findByCode("M").codeName).isEqualTo(Code.MALE.codeName)
    }
}