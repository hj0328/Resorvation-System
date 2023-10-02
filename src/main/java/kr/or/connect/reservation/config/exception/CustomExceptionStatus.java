package kr.or.connect.reservation.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {
    INVALID_REQUEST_ERROR(4001, "잘못된 입력 요청입니다."),

    USER_LOGIN_FAIL(4010, "아이디 또는 비밀번호를 확인해주세요."),
    USER_NOT_FOUND(4011, "사용자를 찾을 수 없습니다."),
    DUPLICATE_USER_EMAIL(4012, "이미 가입된 이메일입니다."),

    RESERVATION_NOT_FOUND(4100, "예약정보를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(4200, "상품정보를 찾을 수 없습니다."),
    PRODUCT_AVERAGE_SCORE_NOT_FOUND(4201, "상품의 평점정보를 찾을 수 없습니다."),


    PRODUCT_DISPLAY_NOT_FOUND(4202, "상품의 전시정보를 찾을 수 없습니다."),
    PRODUCT_DISPLAY_IMAGE_NOT_FOUND(4203, "상품의 전시 이미지 정보를 찾을 수 없습니다."),

    MULTIPLICITY_VIOLATION(4301, "둘 이상의 값을 저장할 수 없습니다."),
    MULTIPLICITY_COMMENTS_VIOLATION(4302, "둘 이상의 메시지를 등록할 수 없습니다."),

    COMMENT_SAVING_FAIL(4401, "댓글을 저장할 수 없습니다."),
    COMMENT_NOT_FOUND(4402, "댓글을 찾을 수 없습니다."),
    COMMENT_IMAGE_NOT_FOUND(4402, "댓글 이미지를 찾을 수 없습니다.")
    ;

    private final Integer code;
    private final String message;

}
