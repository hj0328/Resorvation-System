package kr.or.connect.reservation.config.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private boolean isSuccess;
    private String message;

    public ErrorResponse(boolean b, String defaultErrorMessage) {
    }
}
