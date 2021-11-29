package com.hdjunction.domain.patient;

import com.hdjunction.domain.baseentity.BaseTimeEntity;
import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.visit.Visit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Patient extends BaseTimeEntity {

    private static final StringBuilder sb = new StringBuilder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Visit> visits = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String registrationNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Code gender;

    @Embedded
    private Birthday birthday;

    @Embedded
    private Phone phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Visit> visitHistory = new ArrayList<>();

    public static String generateRegistrationNumber(String number) {
        sb.setLength(0);
        sb.append(LocalDate.now().getYear());
        sb.append(String.format("%05d", Integer.parseInt(number)));
        return sb.toString();
    }
}
