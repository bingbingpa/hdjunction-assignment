package com.hdjunction.domain.patient.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePatientRequest {

    private String name;
    private String gender;
    private String birthDay;
    private String phone;

    @Builder
    public UpdatePatientRequest(String name, String gender, String birthDay, String phone) {
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.phone = phone;
    }
}
