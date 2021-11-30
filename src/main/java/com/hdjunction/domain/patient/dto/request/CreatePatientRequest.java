package com.hdjunction.domain.patient.dto.request;

import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.patient.DateOfBirth;
import com.hdjunction.domain.patient.Patient;
import com.hdjunction.domain.patient.Phone;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePatientRequest {

    private Long hospitalId;
    @NotBlank
    private String name;
    @NotBlank
    private String gender;
    @Pattern(regexp= DateOfBirth.REGEX_DATE_OF_BIRTH)
    private String dateOfBirth;
    @Pattern(regexp=Phone.REGEX_PHONE)
    private String phone;

    @Builder
    public CreatePatientRequest(Long hospitalId, String name, String gender, String dateOfBirth, String phone) {
        this.hospitalId = hospitalId;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
    }

    public Patient toEntity() {
        return Patient.builder()
                .hospital(Hospital.builder().id(hospitalId).build())
                .name(name)
                .gender(Code.findByCode(gender))
                .dateOfBirth(DateOfBirth.from(dateOfBirth))
                .phone(Phone.from(phone))
                .build();
    }
}
