package kr.or.connect.reservation.config.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final CustomExceptionStatus customExceptionStatus;

    public CustomException(CustomExceptionStatus customExceptionStatus) {
        this.customExceptionStatus = customExceptionStatus;
    }
}
