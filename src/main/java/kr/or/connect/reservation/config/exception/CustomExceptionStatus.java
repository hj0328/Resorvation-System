package kr.or.connect.reservation.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {
    INVALID_REQUEST_ERROR(4001, "잘못된 요청입니다."),

    MEMBER_LOGIN_FAIL(4010, "아이디 또는 비밀번호를 확인해주세요."),
    MEMBER_NOT_FOUND(4011, "사용자를 찾을 수 없습니다."),
    DUPLICATE_MEMBER_EMAIL(4012, "이미 가입된 이메일입니다."),

    RESERVATION_NOT_FOUND(4100, "예약정보를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(4200, "상품정보를 찾을 수 없습니다."),
    PRODUCT_AVERAGE_SCORE_NOT_FOUND(4201, "상품의 평점정보를 찾을 수 없습니다."),

    PRODUCT_DISPLAY_NOT_FOUND(4202, "상품의 전시정보를 찾을 수 없습니다."),
    PRODUCT_DISPLAY_IMAGE_NOT_FOUND(4203, "상품의 전시 이미지 정보를 찾을 수 없습니다."),

    MULTIPLICITY_VIOLATION(4301, "둘 이상의 값을 저장할 수 없습니다."),
    MULTIPLICITY_COMMENTS_VIOLATION(4302, "둘 이상의 메시지를 등록할 수 없습니다."),

    COMMENT_SAVING_FAIL(4401, "댓글을 저장할 수 없습니다."),
    COMMENT_NOT_FOUND(4402, "댓글을 찾을 수 없습니다."),
    COMMENT_IMAGE_NOT_FOUND(4402, "댓글 이미지를 찾을 수 없습니다."),

    CATEGORY_NOT_FOUND(4501, "카테고리를 찾을 수 없습니다."),

    PRODUCT_SCHEDULE_NOT_FOUND(4601, "상품 예약정보가 없습니다."),
    DUPLICATE_PRODUCT_SCHEDULE(4610, "중복된 상품 예약정보가 있습니다."),

    PLACE_NOT_FOUND(4701, "장소 정보가 없습니다."),
    NO_EXIST_SEAT_TYPE(4702, "존재하지 않는 좌석 타입입니다."),
    NO_SEAT_AVAILABLE(2703, "사용 가능한 좌석이 없습니다."),

    NO_SESSION_EXIST(4801, "로그인이 필요합니다.");

    private final Integer code;
    private final String message;

}
