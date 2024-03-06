package kr.or.connect.reservation.domain.member;

import kr.or.connect.reservation.config.PasswordEncoder;
import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.domain.member.dao.MemberRepository;
import kr.or.connect.reservation.domain.member.dto.MemberRequest;
import kr.or.connect.reservation.domain.member.dto.MemberResponse;
import kr.or.connect.reservation.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder pwEncoder;

    @InjectMocks
    private MemberService userService;

    @DisplayName("로그인 성공 테스트")
    @Test
    void loginTest() {
        // when
        Member testUser = getTestUser();
        when(memberRepository.findMemberByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        when(pwEncoder.matches("test1", "test1"))
                .thenReturn(true);

        // given
        MemberResponse loginUser = userService.login("test@gmail.com", "test1");

        // then
        assertThat(loginUser.getName()).isEqualTo(testUser.getName());
    }

    @DisplayName("로그인 비밀번호 실패 테스트")
    @Test
    void loginPasswordFailTest() {
        // when
        Member testUser = getTestUser();
        when(memberRepository.findMemberByEmail("test@gmail.com"))
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
        MemberRequest userRequest = MemberRequest.createMemberRequest("test@gmail.com", "test", "test");

        when(memberRepository.save(any()))
                .thenReturn(getTestUser());

        // given
        MemberResponse join = userService.join(userRequest);

        // then
        assertThat(join.getEmail()).isEqualTo("test@gmail.com");
        assertThat(join.getName()).isEqualTo("test");
    }

    @DisplayName("회원가입 중복 이메일 실패 테스트")
    @Test
    void joinDuplicateEmailFailTest() {
        // when
        MemberRequest userRequest = MemberRequest.createMemberRequest("test@gmail.com", "test", "test1");

        Member testUser = getTestUser();
        when(memberRepository.findMemberByEmail("test@gmail.com"))
                .thenReturn(Optional.of(testUser));

        // then
        assertThrows(CustomException.class,
                () -> userService.join(userRequest));
    }

    private Member getTestUser() {
        return Member.create("test@gmail.com", "test", "test1");
    }
}