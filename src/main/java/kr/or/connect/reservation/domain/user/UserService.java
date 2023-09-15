package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.UserRequestDto;
import kr.or.connect.reservation.domain.user.dto.UserResponseDto;
import kr.or.connect.reservation.domain.user.repository.User;

public interface UserService {
    User login(String email, String password);

    UserResponseDto register(UserRequestDto userRequestDto);

    // 등급 업데이트
    void updateUserType(String email, Integer reservationCount);
}
