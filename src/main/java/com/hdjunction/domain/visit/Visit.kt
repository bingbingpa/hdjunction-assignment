package com.hdjunction.domain.visit

import com.hdjunction.domain.code.Code
import com.hdjunction.domain.hospital.Hospital
import com.hdjunction.domain.patient.Patient
import javax.persistence.*

@Entity
class Visit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    val hospital: Hospital,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val visitCode: Code
)