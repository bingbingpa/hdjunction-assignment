package com.hdjunction.domain.patient;

import com.hdjunction.domain.baseentity.BaseTimeEntity;
import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.visit.Visit;
import lombok.AccessLevel;
import lombok.Builder;
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
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, orphanRemoval = true)
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

    @Builder
    public Patient(Hospital hospital, String name, String registrationNo, Code gender, Birthday birthday, Phone phone) {
        this.hospital = hospital;
        this.name = name;
        this.registrationNo = registrationNo;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
    }

    public void generateRegistrationNumber(String number) {
        sb.setLength(0);
        sb.append(LocalDate.now().getYear());
        sb.append(String.format("%05d", Integer.parseInt(number) + 1));
        this.registrationNo = sb.toString();
    }

    public void update(String name, Code gender, Birthday birthday, Phone phone) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public Long getHospitalId() {
        return hospital.getId();
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public String getName() {
        return name;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public String getGender() {
        return gender.getName();
    }

    public String getBirthday() {
        return birthday != null ? birthday.getBirthDay() : "";
    }

    public String getPhone() {
        return phone != null ? phone.getPhone() : "";
    }
}
