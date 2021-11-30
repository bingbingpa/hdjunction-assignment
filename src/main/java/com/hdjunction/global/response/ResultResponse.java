package com.hdjunction.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse<T> {

    private final int status;
    private final T payload;
    private final String errorMessage;

    @Builder
    public ResultResponse(int status, T payload, String errorMessage) {
        this.status = status;
        this.payload = payload;
        this.errorMessage = errorMessage;
    }
}
