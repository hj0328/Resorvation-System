package kr.or.connect.reservation.domain.member;

import kr.or.connect.reservation.config.PasswordEncoder;
import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.domain.member.dao.MemberDao;
import kr.or.connect.reservation.domain.member.dto.Member;
import kr.or.connect.reservation.domain.member.dto.MemberRequest;
import kr.or.connect.reservation.domain.member.dto.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberDao userDao;

    @Mock
    private PasswordEncoder pwEncoder;

    @InjectMocks
    private MemberService userService;

    @DisplayName("로그인 성공 테스트")
    @Test
    void loginTest() {
        // when
        Member testUser = getTestUser();
        when(userDao.findMemberByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        when(pwEncoder.matches("test1", "test1"))
                .thenReturn(true);

        // given
        Member loginUser = userService.login("test@gmail.com", "test1");

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
        Member testUser = getTestUser();
        when(userDao.findMemberByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        // then
        assertThrows(CustomException.class,
                () -> userService.login("test@gmail.com", "wrongPassword") );
    }

    @DisplayName("로그인 조회 실패 테스트")
    @Test
    void loginUserNotFoundTest() {
        // when
        Member testUser = getTestUser();

        // then
        assertThrows(CustomException.class,
                () -> userService.login("test@gmail.com", "wrongPassword") );
    }

    @DisplayName("회원가입 성공 테스트")
    @Test
    void joinTest() {
        // when
        MemberRequest userRequest = new MemberRequest();
        userRequest.setEmail("test@gmail.com");
        userRequest.setName("test");
        userRequest.setPassword("test1");

        when(userDao.saveMember(any()))
                .thenReturn(1);

        // given
        MemberResponse join = userService.join(userRequest);

        // then
        assertThat(join.getId()).isEqualTo(1);
        assertThat(join.getEmail()).isEqualTo("test@gmail.com");
        assertThat(join.getName()).isEqualTo("test");
    }

    @DisplayName("회원가입 중복 이메일 실패 테스트")
    @Test
    void joinDuplicateEmailFailTest() {
        // when
        MemberRequest userRequest = new MemberRequest();
        userRequest.setEmail("test@gmail.com");
        userRequest.setName("test");
        userRequest.setPassword("test1");

        Member testUser = getTestUser();
        when(userDao.findMemberByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        // then
        assertThrows(CustomException.class,
                () -> userService.join(userRequest));
    }

    private Member getTestUser() {
        Member user = new Member();
        user.setId(1);
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