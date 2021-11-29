package com.hdjunction.domain.patient;

public interface PatientRepositoryCustom {

    Long findMaxPatientIdByHospitalId(Long hospitalId);
}
