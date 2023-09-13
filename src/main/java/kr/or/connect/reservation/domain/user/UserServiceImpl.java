package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.UserDto;
import kr.or.connect.reservation.domain.user.dto.UserRequestDto;
import kr.or.connect.reservation.domain.user.dto.UserResponseDto;
import kr.or.connect.reservation.utils.UtilConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder pwEncoder;

    @Override
    public UserDto login(String email, String password) {
        String encodePwd = userDao.selectUserPassword(email);
        if (!pwEncoder.matches(password, encodePwd)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호를 잘못입력하였습니다.");
        }

        return userDao.selectUserByEmail(email);
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
        userDto.setTotalReservationCount(0);

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
    public void updateUserType(String email, Integer addReservationCount) {
        Integer reservedCount = getUserTotalReservationCount(email);

        if (addReservationCount == null || reservedCount == null) {
            throw new IllegalArgumentException("Illegal reservedCount");
        }
        Integer newReservationCount = reservedCount + addReservationCount;

        UserType userType = userDao.selectUserTypeByEmail(email);
        if(newReservationCount >= UtilConstant.VVIP_RESERVATION_COUNT) {
            userType = UserType.VVIP;
        } else if(newReservationCount >= UtilConstant.VIP_RESERVATION_COUNT) {
            userType = userType.VIP;
        }

        userDao.updateTypeAndTotalReservationCount(email, userType, newReservationCount);
    }

    private Integer getUserTotalReservationCount(String email) {
        return userDao.selectUserTotalReservationCount(email);
    }
}
