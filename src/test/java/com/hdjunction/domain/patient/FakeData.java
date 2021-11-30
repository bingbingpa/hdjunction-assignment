package com.hdjunction.domain.patient;

import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.hospital.Hospital;
import com.hdjunction.domain.visit.Visit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FakeData {

    public static Hospital getHospital() {
        return Hospital.builder()
                .name("병원")
                .directorName("홍길동")
                .ykiho("1234567")
                .build();
    }

    public static List<Patient.PatientBuilder> getPatients(int maxValue) {
        List<Patient.PatientBuilder> list = new ArrayList<>();

        IntStream.rangeClosed(1, maxValue)
                .forEach(i ->
                        list.add(
                                Patient.builder()
                                        .registrationNo(String.valueOf(i))
                                        .name("test" + i)
                                        .birthday(Birthday.from("20200101"))
                                        .phone(Phone.from("01012345678"))
                                        .gender(Code.MALE)
                        )
                );
        return list;
    }

    public static List<Visit.VisitBuilder> getVisits(int maxValue) {
        List<Visit.VisitBuilder> list = new ArrayList<>();
        IntStream.rangeClosed(1, maxValue)
                .forEach(i ->
                        list.add(Visit.builder().visitCode(Code.VISITING))
                );
        return list;
    }
}
