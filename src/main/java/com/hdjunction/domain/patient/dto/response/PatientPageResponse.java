package com.hdjunction.domain.patient.dto.response;

import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.patient.DateOfBirth;
import com.hdjunction.domain.patient.Phone;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientPageResponse {

    private Long id;
    private Long hospitalId;
    private String name;
    private String registrationNo;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private String receptionDate;

    public PatientPageResponse(Long id, Long hospitalId, String name, String registrationNo, Code gender, DateOfBirth dateOfBirth, Phone phone, LocalDateTime receptionDate) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.name = name;
        this.registrationNo = registrationNo;
        this.gender = gender.getName();
        this.dateOfBirth = dateOfBirth.getDateOfBirth();
        this.phone = phone.getPhone();
        this.receptionDate = receptionDate != null ? receptionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
    }
}
