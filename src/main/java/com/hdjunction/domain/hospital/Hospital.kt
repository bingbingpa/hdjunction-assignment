package com.hdjunction.domain.hospital

import com.hdjunction.domain.baseentity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

class Hospital(
    @Id @GeneratedValue(strategy = IDENTITY) @Column(name = "hospital_id")
    var id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val ykiho: String,

    @Column(nullable = false)
    val directorName: String
) : BaseTimeEntity()