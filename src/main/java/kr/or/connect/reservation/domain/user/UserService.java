package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserRequest;
import kr.or.connect.reservation.domain.user.dto.UserResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    User login(String email, String password);

    UserResponse join(UserRequest userRequestDto);


    /*
        사용자의 타입 업데이트
        예매 건수에 따라 VIP, VVIP 로 등급이 변동된다.
     */
    @Transactional
    void updateUserGrade(Integer userId, Integer addReservationCount) throws CustomException;
}
