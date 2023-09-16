package kr.or.connect.reservation.domain.user.dto;

import kr.or.connect.reservation.domain.user.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer userId;
    private String email;
    private String name;
    private String password;
    private UserType type;
    private Integer totalReservationCount;
}
