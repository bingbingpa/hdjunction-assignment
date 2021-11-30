package com.hdjunction.domain.patient;

import com.hdjunction.domain.patient.dto.response.PatientPageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hdjunction.domain.hospital.QHospital.hospital;
import static com.hdjunction.domain.patient.QPatient.patient;
import static com.hdjunction.domain.visit.QVisit.visit;

@RequiredArgsConstructor
public class PatientRepositoryImpl implements PatientRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findMaxPatientIdByHospitalId(Long hospitalId) {
        return jpaQueryFactory
                .select(patient.id.max())
                .from(patient)
                .leftJoin(hospital).on(hospital.eq(patient.hospital))
                .where(hospital.id.eq(hospitalId))
                .fetchFirst();
    }

    @Override
    public Page<PatientPageResponse> search(PatientSearchType type, String value, Pageable pageable) {
        List<PatientPageResponse> fetch = jpaQueryFactory
                .select(Projections.constructor(PatientPageResponse.class,
                        patient.id,
                        patient.hospital.id,
                        patient.name,
                        patient.registrationNo,
                        patient.gender,
                        patient.dateOfBirth,
                        patient.phone,
                        visit.createdDate.max()
                ))
                .from(patient)
                .leftJoin(visit).on(visit.patient.id.eq(patient.id))
                .groupBy(patient)
                .where(type.getEq(value))
                .limit(pageable.getPageSize())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .fetch();
        return new PageImpl<>(fetch, pageable, getTotalCount(type, value));
    }

    private Long getTotalCount(PatientSearchType type, String value) {
        return jpaQueryFactory
                .select(patient.id.count())
                .from(patient)
                .where(type.getEq(value))
                .fetchOne();
    }
}
