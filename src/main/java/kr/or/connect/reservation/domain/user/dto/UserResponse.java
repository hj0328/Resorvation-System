package kr.or.connect.reservation.domain.user.dto;

import kr.or.connect.reservation.domain.user.UserGrade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer userId;
    private String name;
    private String email;
    private UserGrade grade;
}
