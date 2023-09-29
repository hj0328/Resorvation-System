package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserRequest;
import kr.or.connect.reservation.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static kr.or.connect.reservation.utils.UtilConstant.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder pwEncoder;

    @Override
    public User login(String email, String password) {
        User user = userDao.selectUserByEmail(email)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));
        String encodePwd = user.getPassword();
        if (!pwEncoder.matches(password, encodePwd)) {
            throw new CustomException(CustomExceptionStatus.USER_LOGIN_FAIL);
        }

        return user;
    }

    @Transactional
    @Override
    public UserResponse join(UserRequest userRequestDto) {
        String encodePwd = pwEncoder.encode(userRequestDto.getPassword());

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(encodePwd);
        user.setType(UserGrade.BASIC);
        user.setTotalReservationCount(DEFAULT_TOTAL_RESERVATION_COUNT);
        user.setCreateDate(LocalDateTime.now());
        user.setModifyDate(LocalDateTime.now());

        int id = userDao.insertUser(user);
        UserResponse newUserRequest = new UserResponse();
        newUserRequest.setUserId(id);
        newUserRequest.setEmail(user.getEmail());
        newUserRequest.setName(user.getName());
        newUserRequest.setType(user.getType());
        return newUserRequest;
    }

    // 중복 로그인 검사
    // 이메일, 전화번호 중복되면 안 됨


    /*
        사용자의 타입 업데이트
        예매 건수에 따라 VIP, VVIP 로 등급이 변동된다.
     */
    @Transactional
    @Override
    public void updateUserGrade(Integer userId, Integer addReservationCount) throws CustomException {
        Integer reservedCount = getUserTotalReservationCount(userId);

        Integer newReservationCount = reservedCount + addReservationCount;

        UserGrade userType = userDao.selectUserTypeById(userId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));

        if(newReservationCount >= VVIP_RESERVATION_COUNT) {
            userType = UserGrade.VVIP;
        } else if(newReservationCount >= VIP_RESERVATION_COUNT) {
            userType = userType.VIP;
        }

        userDao.updateTypeAndTotalReservationCountById(userId, userType, newReservationCount);
    }

    private Integer getUserTotalReservationCount(Integer userId) {
        Integer totalReservationCount = userDao.selectUserTotalReservationCountById(userId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));
        return totalReservationCount;
    }
}
