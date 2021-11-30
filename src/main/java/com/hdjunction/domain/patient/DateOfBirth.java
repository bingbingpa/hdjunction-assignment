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
public class DateOfBirth {

    public static final String REGEX_DATE_OF_BIRTH = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
    private static final Pattern pattern = Pattern.compile(REGEX_DATE_OF_BIRTH);

    @Column
    private String dateOfBirth;

    public DateOfBirth(String dateOfBirth) {
        if (isNull(dateOfBirth)) {
            new DateOfBirth();
            return;
        }
        validation(dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }

    public static DateOfBirth from(String dateOfBirth) {
        return new DateOfBirth(dateOfBirth);
    }

    public String getDateOfBirth() {
        return isNull(dateOfBirth) ? "" : parseDateOfBirth(dateOfBirth);
    }

    private String parseDateOfBirth(String dateOfBirth) {
        return LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
    }

    private static void validation(String dateOfBirth) {
        if (!pattern.matcher(dateOfBirth).find()) {
            throw new IllegalArgumentException("올바르지 않은 생년월입입니다. ex) 20200102");
        }
    }

    private boolean isNull(String dateOfBirth) {
        return dateOfBirth == null;
    }
}
