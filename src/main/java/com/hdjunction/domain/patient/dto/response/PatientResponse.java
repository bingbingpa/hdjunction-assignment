package com.hdjunction.domain.patient.dto.response;

import com.hdjunction.domain.patient.Patient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientResponse {

    private Long id;
    private Long hospitalId;
    private String name;
    private String registrationNo;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private List<VisitResponse> visits;

    public PatientResponse(Patient patient) {
        this.id = patient.getId();
        this.hospitalId = patient.getHospitalId();
        this.name = patient.getName();
        this.registrationNo = patient.getRegistrationNo();
        this.gender = patient.getGender();
        this.dateOfBirth = patient.getDateOfBirth();
        this.phone = patient.getPhone();
        this.visits = patient.getVisits().stream()
                .map(v -> new VisitResponse(v.getId(), v.getCreatedDate(), v.getVisitCode()))
                .collect(Collectors.toList());
    }
}
