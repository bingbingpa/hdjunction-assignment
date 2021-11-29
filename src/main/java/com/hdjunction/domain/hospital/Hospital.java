package com.hdjunction.domain.hospital;

import com.hdjunction.domain.baseentity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ykiho;

    @Column(nullable = false)
    private String directorName;

    @Builder
    public Hospital(String name, String ykiho, String directorName) {
        this.name = name;
        this.ykiho = ykiho;
        this.directorName = directorName;
    }
}
