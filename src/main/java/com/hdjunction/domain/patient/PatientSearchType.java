package com.hdjunction.domain.patient;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Function;

import static com.hdjunction.domain.patient.QPatient.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum PatientSearchType {

    NAME(patient.name::eq),
    REGISTRATION_NO(patient.registrationNo::eq),
    BIRTHDAY(str-> patient.birthday.eq(Birthday.from(str)));

    private Function<String, BooleanExpression> expression;

    PatientSearchType(Function<String, BooleanExpression> expression) {
        this.expression = expression;
    }

    public BooleanExpression getEq(String value) {
        return expression.apply(value);
    }
}
