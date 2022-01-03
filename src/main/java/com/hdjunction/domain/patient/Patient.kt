package com.hdjunction.domain.patient

import com.hdjunction.domain.baseentity.BaseTimeEntity
import com.hdjunction.domain.code.Code
import com.hdjunction.domain.hospital.Hospital
import com.hdjunction.domain.visit.Visit
import javax.persistence.*

@Entity
class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    val hospital: Hospital,

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, orphanRemoval = true)
    val visits: List<Visit> = ArrayList(),

    @Column(nullable = false)
    val name: String,

    @Column(unique = true, nullable = false)
    val registrationNo: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val gender: Code,

    @Embedded
    val dateOfBirth: DateOfBirth,

    @Embedded
    val phone: Phone
) : BaseTimeEntity() {
    companion object {
        val sb: StringBuilder = StringBuilder()
    }
}