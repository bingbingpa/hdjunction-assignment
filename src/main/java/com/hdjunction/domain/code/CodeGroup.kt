package com.hdjunction.domain.code

import com.hdjunction.domain.code.Code.*
import java.util.Collections.emptyList
import java.util.stream.Collectors


enum class CodeGroup(
    val codeGroup: String,
    val codeName: String,
    private val codes: List<Code>
) {
    GENDER("성별코드", "성별을 표시", listOf(MALE, FEMALE)),
    VISIT("방문상태코드", "환자방문의 상태(방문중, 종료, 취소)", listOf(VISITING, END, CANCEL)),
    MEDICAL_DEPARTMENT("진료과목코드", "진료과목(내과, 안과 등)", listOf(INTERNAL_MEDICINE, OPHTHALMOLOGY)),
    MEDICAL_TYPE("진료유형코드", "진료유형(약처방, 검사등)", listOf(PRESCRIPTION, EXAMINATION)),
    NONE("NONE", "일치하는 코드그룹 없음", emptyList());

    fun getCodeGroups(): String {
        return codes.stream()
            .map { it.code + ": " + it.codeName }
            .collect(Collectors.joining(", "))
    }
}