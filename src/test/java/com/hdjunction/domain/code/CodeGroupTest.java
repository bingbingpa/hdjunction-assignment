package com.hdjunction.domain.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeGroupTest {

    @Test
    @DisplayName("코드 그룹 join")
    void code_group_join() {
        assertThat(CodeGroup.GENDER.getCodeGroup()).isEqualTo("M: 남, F: 여");
    }
}