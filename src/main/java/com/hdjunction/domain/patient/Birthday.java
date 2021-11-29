package com.hdjunction.domain.patient;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Birthday {

    private static final Pattern pattern = Pattern.compile("^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$");

    @Column
    private String birthDay;

    public Birthday(String birthDay) {
        this.birthDay = birthDay;
    }

    public static Birthday from(String birthDay) {
        validation(birthDay);
        return new Birthday(parseBirthDay(birthDay));
    }

    public String getBirthDay() {
        return birthDay;
    }

    private static String parseBirthDay(String birthDay) {
        return LocalDate.parse(birthDay, DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
    }

    private static void validation(String birthDay) {
        if (!pattern.matcher(birthDay).find()) {
            throw new IllegalArgumentException("올바르지 않은 생년월입입니다. ex) 20200102");
        }
    }
}
