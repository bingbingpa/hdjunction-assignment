package com.hdjunction.domain.patient;

public class NotFoundPatientException extends RuntimeException {

    public NotFoundPatientException() {
    }

    public NotFoundPatientException(Long id) {
        super("존재하지 않는 환자 입니다. ==== " + id);
    }
}
