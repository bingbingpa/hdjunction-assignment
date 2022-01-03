package com.hdjunction.domain.code

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class CodeGroupTest {

    @Test
    @DisplayName("코드 그룹 join")
    fun `code group join`() {
        assertThat(CodeGroup.GENDER.getCodeGroups()).isEqualTo("M: 남, F: 여")
    }
}