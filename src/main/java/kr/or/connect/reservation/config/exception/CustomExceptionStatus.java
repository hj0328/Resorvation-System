package kr.or.connect.reservation.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {
    REQUEST_ERROR(4000, "잘못된 요청입니다."),

    USER_LOGIN_FAIL(4010, "아이디 또는 비밀번호를 확인해주세요."),
    USER_NOT_FOUND(4011, "사용자를 찾을 수 없습니다."),

    RESERVATION_NOT_FOUND(4100, "예약정보를 찾을 수 없습니다.");
    ;

    private final Integer code;
    private final String message;

}
