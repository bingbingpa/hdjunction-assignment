package com.hdjunction.domain.patient;

import com.hdjunction.domain.patient.dto.response.PatientPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientRepositoryCustom {

    Long findMaxPatientIdByHospitalId(Long hospitalId);

    Page<PatientPageResponse> search(PatientSearchType type, String value, Pageable pageable);
}
