package kr.or.connect.reservation.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private UserGrade grade;
}
