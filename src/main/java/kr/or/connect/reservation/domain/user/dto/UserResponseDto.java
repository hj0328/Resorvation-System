package kr.or.connect.reservation.domain.user.dto;

import kr.or.connect.reservation.domain.user.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponseDto {
    private Integer userId;
    private String name;
    private String email;
    private UserType type;
}
