package com.hdjunction.domain.patient.dto.request;

import com.hdjunction.domain.patient.Birthday;
import com.hdjunction.domain.patient.Phone;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePatientRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String gender;
    @Pattern(regexp= Birthday.REGEX_BIRTHDAY)
    private String birthDay;
    @Pattern(regexp= Phone.REGEX_PHONE)
    private String phone;

    @Builder
    public UpdatePatientRequest(String name, String gender, String birthDay, String phone) {
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.phone = phone;
    }
}
