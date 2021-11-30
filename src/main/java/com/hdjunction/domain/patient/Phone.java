package com.hdjunction.domain.patient;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {

    public static final String REGEX_PHONE = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
    private static final Pattern pattern = Pattern.compile(REGEX_PHONE);

    @Column
    private String phone;

    public Phone(String phone) {
        if (isNull(phone)) {
            new Phone();
            return;
        }
        validation(phone);
        this.phone = phone;
    }

    public static Phone from(String phone) {
        return new Phone(phone);
    }

    public String getPhone() {
        return isNull(phone) ? "" : parsePhoneNumber(phone);
    }

    private String parsePhoneNumber(String phone) {
        return phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
    }

    private static void validation(String phone) {
        if (!pattern.matcher(phone).find()) {
            throw new IllegalArgumentException("올바르지 않은 전화번호입니다. ex) 01012341234");
        }
    }

    private boolean isNull(String phone) {
        return phone == null;
    }
}
