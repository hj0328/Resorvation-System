package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.user.dto.UserDto;
import kr.or.connect.reservation.domain.user.dto.UserRequestDto;
import kr.or.connect.reservation.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.or.connect.reservation.utils.UtilConstant.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder pwEncoder;

    @Override
    public UserDto login(String email, String password) {
        UserDto user = userDao.selectUserByEmail(email)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));
        String encodePwd = user.getPassword();
        if (!pwEncoder.matches(password, encodePwd)) {
            throw new CustomException(CustomExceptionStatus.USER_LOGIN_FAIL);
        }

        return user;
    }

    @Transactional
    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        String encodePwd = pwEncoder.encode(userRequestDto.getPassword());

        UserDto userDto = new UserDto();
        userDto.setName(userRequestDto.getName());
        userDto.setEmail(userRequestDto.getEmail());
        userDto.setPassword(encodePwd);
        userDto.setType(UserType.BASIC);
        userDto.setTotalReservationCount(DEFAULT_TOTAL_RESERVATION_COUNT);

        userDao.insertUser(userDto);
        UserResponseDto newUserRequest = new UserResponseDto();
        newUserRequest.setEmail(userDto.getEmail());
        newUserRequest.setName(userDto.getName());
        newUserRequest.setType(userDto.getType());
        return newUserRequest;
    }

    /*
        사용자의 타입 업데이트
        예매 건수에 따라 VIP, VVIP 로 등급이 변동된다.
     */
    @Transactional
    @Override
    public void updateUserType(Integer userId, Integer addReservationCount) throws CustomException {
        Integer reservedCount = getUserTotalReservationCount(userId);

        Integer newReservationCount = reservedCount + addReservationCount;

        UserType userType = userDao.selectUserTypeById(userId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));

        if(newReservationCount >= VVIP_RESERVATION_COUNT) {
            userType = UserType.VVIP;
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
