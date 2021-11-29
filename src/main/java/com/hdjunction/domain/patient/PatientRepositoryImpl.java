package com.hdjunction.domain.patient;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.hdjunction.domain.hospital.QHospital.hospital;
import static com.hdjunction.domain.patient.QPatient.patient;

@RequiredArgsConstructor
public class PatientRepositoryImpl implements PatientRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findMaxPatientIdByHospitalId(Long hospitalId) {
        return jpaQueryFactory.select(patient.id)
                .from(patient)
                .leftJoin(hospital).on(hospital.eq(patient.hospital))
                .where(hospital.id.eq(hospitalId))
                .orderBy(patient.id.desc())
                .limit(1)
                .fetchFirst();
    }
}
