package com.hdjunction.domain.code;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.hdjunction.domain.code.Code.*;

public enum CodeGroup {
    GENDER("성별코드", "성별을 표시", Arrays.asList(MALE, FEMALE)),
    VISIT("방문상태코드", "환자방문의 상태(방문중, 종료, 취소)", Arrays.asList(VISITING, END, CANCEL)),
    MEDICAL_DEPARTMENT("진료과목코드", "진료과목(내과, 안과 등)", Arrays.asList(INTERNAL_MEDICINE, OPHTHALMOLOGY)),
    MEDICAL_TYPE("진료유형코드", "진료유형(약처방, 검사등)", Arrays.asList(PRESCRIPTION, EXAMINATION)),
    NONE("NONE", "일치하는 코드그룹 없음", Collections.emptyList());

    private final String codeGroup;
    private final String name;
    private final List<Code> codes;

    CodeGroup(String codeGroup, String name, List<Code> codes) {
        this.codeGroup = codeGroup;
        this.name = name;
        this.codes = codes;
    }

    public String getCodeGroup() {
        return codes.stream()
                .map(f -> f.getCode() + ": " + f.getName())
                .collect(Collectors.joining(", "));
    }
}
