package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.UserDto;
import kr.or.connect.reservation.domain.user.dto.UserRequestDto;
import kr.or.connect.reservation.domain.user.dto.UserResponseDto;

public interface UserService {
    UserDto login(String email, String password);

    UserResponseDto register(UserRequestDto userRequestDto);

    // 등급 업데이트
    void updateUserType(String email, Integer reservationCount);
}
