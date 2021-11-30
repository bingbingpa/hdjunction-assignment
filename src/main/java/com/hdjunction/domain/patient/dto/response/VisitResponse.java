package com.hdjunction.domain.patient.dto.response;

import com.hdjunction.domain.code.Code;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitResponse {

    private Long id;
    private String receptionDate;
    private String visitCode;

    public VisitResponse(Long id, LocalDateTime receptionDate, Code visitCode) {
        this.id = id;
        this.receptionDate = receptionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.visitCode = visitCode.getName();
    }
}
