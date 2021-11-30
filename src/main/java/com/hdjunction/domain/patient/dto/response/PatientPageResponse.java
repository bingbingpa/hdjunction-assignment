package com.hdjunction.domain.patient.dto.response;

import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.patient.Birthday;
import com.hdjunction.domain.patient.Phone;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String birthDay;
    private String phone;
    private String receptionDate;

    public PatientPageResponse(Long id, Long hospitalId, String name, String registrationNo, Code gender, Birthday birthDay, Phone phone, LocalDateTime receptionDate) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.name = name;
        this.registrationNo = registrationNo;
        this.gender = gender.getName();
        this.birthDay = birthDay.getBirthDay();
        this.phone = phone!= null ? phone.getPhone() : "";
        this.receptionDate = receptionDate != null ? receptionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
    }
}
