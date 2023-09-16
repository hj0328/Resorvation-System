package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.domain.user.dto.UserDto;
import kr.or.connect.reservation.domain.user.dto.UserRequestDto;
import kr.or.connect.reservation.domain.user.dto.UserResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserDto login(String email, String password);

    UserResponseDto register(UserRequestDto userRequestDto);


    /*
        사용자의 타입 업데이트
        예매 건수에 따라 VIP, VVIP 로 등급이 변동된다.
     */
    @Transactional
    void updateUserType(Integer userId, Integer addReservationCount) throws CustomException;
}
