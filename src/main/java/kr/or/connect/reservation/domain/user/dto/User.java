package kr.or.connect.reservation.domain.user.dto;

import kr.or.connect.reservation.domain.user.UserGrade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private Integer userId;
    private String email;
    private String name;
    private String password;
    private UserGrade type;
    private Integer totalReservationCount;
    private String reservationInfoId;
    private String reservationUerCommentId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
