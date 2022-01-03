package com.hdjunction.domain.hospital

import com.hdjunction.domain.baseentity.BaseTimeEntity
import javax.persistence.*

@Entity
class Hospital(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    val id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val ykiho: String,

    @Column(nullable = false)
    val directorName: String
) : BaseTimeEntity()