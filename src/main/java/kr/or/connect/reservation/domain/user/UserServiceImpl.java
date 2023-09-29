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
import java.util.Optional;

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
        // 이미 가입된 이메일 검사
        String email = userRequestDto.getEmail();
        Optional<User> joinedUser = userDao.selectUserByEmail(email);
        if (joinedUser.isPresent()) {
            throw new CustomException(CustomExceptionStatus.DUPLICATE_USER_EMAIL);
        }

        String encodePwd = pwEncoder.encode(userRequestDto.getPassword());

        User joinUser = new User();
        joinUser.setName(userRequestDto.getName());
        joinUser.setEmail(userRequestDto.getEmail());
        joinUser.setPassword(encodePwd);
        joinUser.setType(UserGrade.BASIC);
        joinUser.setTotalReservationCount(DEFAULT_TOTAL_RESERVATION_COUNT);
        joinUser.setCreateDate(LocalDateTime.now());
        joinUser.setModifyDate(LocalDateTime.now());

        int id = userDao.insertUser(joinUser);
        UserResponse newUserRequest = new UserResponse();
        newUserRequest.setUserId(id);
        newUserRequest.setEmail(joinUser.getEmail());
        newUserRequest.setName(joinUser.getName());
        newUserRequest.setType(joinUser.getType());
        return newUserRequest;
    }

    /*
        사용자의 타입 업데이트
        예매 건수에 따라 VIP, VVIP 로 등급이 변동된다.
     */
    @Transactional
    @Override
    public void updateUserGrade(Integer userId, Integer addReservationCount) throws CustomException {
        Integer reservedCount = getUserTotalReservationCount(userId);

        Integer newReservationCount = reservedCount + addReservationCount;

        UserGrade userGrade = userDao.selectUserTypeById(userId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));

        if(newReservationCount >= VVIP_RESERVATION_COUNT) {
            userGrade = UserGrade.VVIP;
        } else if(newReservationCount >= VIP_RESERVATION_COUNT) {
            userGrade = userGrade.VIP;
        }

        userDao.updateTypeAndTotalReservationCountById(userId, userGrade, newReservationCount);
    }

    private Integer getUserTotalReservationCount(Integer userId) {
        Integer totalReservationCount = userDao.selectUserTotalReservationCountById(userId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));
        return totalReservationCount;
    }
}
