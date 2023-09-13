package kr.or.connect.reservation.domain.user.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRequestDto {
    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String password;
}
