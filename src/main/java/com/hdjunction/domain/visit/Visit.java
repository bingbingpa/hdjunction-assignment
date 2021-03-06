package com.hdjunction.domain.visit;

import com.hdjunction.domain.baseentity.BaseTimeEntity;
import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.patient.Patient;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Code visitCode;

    @Builder
    public Visit(Hospital hospital, Patient patient, Code visitCode) {
        this.hospital = hospital;
        this.patient = patient;
        this.visitCode = visitCode;
    }
}
