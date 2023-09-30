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
    private UserGrade grade;
    private Integer totalReservationCount;
    private Integer reservationInfoId;
    private Integer reservationUerCommentId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
