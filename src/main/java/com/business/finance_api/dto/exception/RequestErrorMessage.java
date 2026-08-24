package com.business.finance_api.dto.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record RequestErrorMessage(
        LocalDateTime timestamp,
        Integer status,
        @JsonProperty("reason_phrase")
        String reasonPhrase,
        String message
) {
    @Override
    public String toString() {
        return "RequestErrorMessage{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", reasonPhrase='" + reasonPhrase + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
