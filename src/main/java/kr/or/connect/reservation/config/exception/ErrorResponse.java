package kr.or.connect.reservation.config.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {
    private final String message;
    private final List<String> reasons;

    public ErrorResponse(String message, List<String> reasons) {
        this.message = message;
        this.reasons = reasons;
    }
}
