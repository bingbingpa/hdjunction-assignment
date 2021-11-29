package com.hdjunction.domain.code;

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

    private final String code;
    private final String name;

    Code(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
