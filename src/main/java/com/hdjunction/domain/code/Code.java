package com.hdjunction.domain.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Code {
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

    private static final Code[] CACHED_CODES = Code.values();

    private final String code;
    private final String name;

    Code(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Code findByCode(String code) {
        return Arrays.stream(CACHED_CODES)
                .filter(c -> c.code.equals(code))
                .findAny()
                .orElse(NONE);
    }
}
