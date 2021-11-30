package com.hdjunction.domain.patient.dto.request;

import com.hdjunction.domain.patient.PatientSearchType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchPatientRequest {

    private PatientSearchType type;
    private String value;
}
