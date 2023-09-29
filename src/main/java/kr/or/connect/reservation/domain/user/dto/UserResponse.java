package kr.or.connect.reservation.domain.user.dto;

import kr.or.connect.reservation.domain.user.UserGrade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    private Integer userId;
    private String name;
    private String email;
    private UserGrade type;
}
