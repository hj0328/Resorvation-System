package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserGrade;
import kr.or.connect.reservation.domain.user.dto.UserRequest;
import kr.or.connect.reservation.domain.user.dto.UserResponse;
import kr.or.connect.reservation.domain.user.dao.UserDaoJdbcTemplate;
import kr.or.connect.reservation.utils.UtilConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDaoJdbcTemplate userDao;

    @Mock
    private PasswordEncoder pwEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @DisplayName("로그인 성공 테스트")
    @Test
    void loginTest() {
        // when
        User testUser = getTestUser();
        when(userDao.findUserByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        when(pwEncoder.matches("test1", "test1"))
                .thenReturn(true);

        // given
        User loginUser = userService.login("test@gmail.com", "test1");

        // then
        assertThat(loginUser.getId()).isEqualTo(testUser.getId());
        assertThat(loginUser.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(loginUser.getName()).isEqualTo(testUser.getName());
        assertThat(loginUser.getReservationInfoId()).isEqualTo(testUser.getReservationInfoId());
        assertThat(loginUser.getTotalReservationCount()).isEqualTo(testUser.getTotalReservationCount());
        assertThat(loginUser.getReservationUerCommentId()).isEqualTo(testUser.getReservationUerCommentId());
        assertThat(loginUser.getModifyDate()).isEqualTo(testUser.getModifyDate());
        assertThat(loginUser.getCreateDate()).isEqualTo(testUser.getCreateDate());
    }

    @DisplayName("로그인 비밀번호 실패 테스트")
    @Test
    void loginPasswordFailTest() {
        // when
        User testUser = getTestUser();
        when(userDao.findUserByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        // then
        assertThrows(CustomException.class,
                () -> userService.login("test@gmail.com", "wrongPassword") );
    }

    @DisplayName("로그인 조회 실패 테스트")
    @Test
    void loginUserNotFoundTest() {
        // when
        User testUser = getTestUser();

        // then
        assertThrows(CustomException.class,
                () -> userService.login("test@gmail.com", "wrongPassword") );
    }

    @DisplayName("회원가입 성공 테스트")
    @Test
    void joinTest() {
        // when
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@gmail.com");
        userRequest.setName("test");
        userRequest.setPassword("test1");

        when(userDao.saveUser(any()))
                .thenReturn(1);

        // given
        UserResponse join = userService.join(userRequest);

        // then
        assertThat(join.getId()).isEqualTo(1);
        assertThat(join.getEmail()).isEqualTo("test@gmail.com");
        assertThat(join.getGrade()).isEqualTo(UserGrade.BASIC);
        assertThat(join.getName()).isEqualTo("test");
    }

    @DisplayName("회원가입 중복 이메일 실패 테스트")
    @Test
    void joinDuplicateEmailFailTest() {
        // when
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@gmail.com");
        userRequest.setName("test");
        userRequest.setPassword("test1");

        User testUser = getTestUser();
        when(userDao.findUserByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        // then
        assertThrows(CustomException.class,
                () -> userService.join(userRequest));
    }

    @DisplayName("사용자 등급 VIP 업데이트")
    @Test
    void updateUserGradeVIPTest() {
        // given
        when(userDao.findUserTypeById(1))
                .thenReturn(Optional.of(UserGrade.BASIC));

        when(userDao.findUserTotalReservationCountById(1))
                .thenReturn(Optional.of(1));

        // when
        UserGrade userGrade = userService.updateUserGrade(1, UtilConstant.VIP_RESERVATION_COUNT);

        // then
        assertThat(userGrade).isEqualTo(UserGrade.VIP);
    }

    @DisplayName("사용자 등급 VVIP 업데이트")
    @Test
    void updateUserGradeVVIPTest() {
        // given
        when(userDao.findUserTypeById(1))
                .thenReturn(Optional.of(UserGrade.BASIC));

        when(userDao.findUserTotalReservationCountById(1))
                .thenReturn(Optional.of(1));

        // when
        UserGrade userGrade = userService.updateUserGrade(1, UtilConstant.VVIP_RESERVATION_COUNT);

        // then
        assertThat(userGrade).isEqualTo(UserGrade.VVIP);
    }

    private User getTestUser() {
        User user = new User();
        user.setId(1);
        user.setGrade(UserGrade.BASIC);
        user.setName("tester");
        user.setEmail("test@gmail.com");
        user.setPassword("test1");
        user.setTotalReservationCount(Integer.valueOf(1));
        user.setReservationInfoId(1);
        user.setReservationUerCommentId(1);
        user.setModifyDate(LocalDateTime.of(2023, 1, 1, 1, 1));
        user.setCreateDate(LocalDateTime.of(2023, 1, 1, 1, 1));
        return user;
    }
}