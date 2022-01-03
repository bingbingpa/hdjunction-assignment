package com.hdjunction.domain.code

import java.util.*

enum class Code(
    val code: String,
    val codeName: String
) {
    MALE("M", "남"),
    FEMALE("F", "여"),

    VISITING("1", "방문중"),
    END("2", "종료"),
    CANCEL("3", "취소"),

    INTERNAL_MEDICINE("01", "내과"),
    OPHTHALMOLOGY("02", "안과"),

    PRESCRIPTION("D", "약처방"),
    EXAMINATION("T", "검사"),

    NONE("NONE", "일치하는 코드 없음");

    companion object {
        private val CACHED_CODES = values()
        fun findByCode(code: String): Code {
            return Arrays.stream(CACHED_CODES)
                .filter { it.code == code }
                .findAny()
                .orElse(NONE)
        }
    }
}