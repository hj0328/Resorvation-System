package kr.or.connect.reservation.domain.member;

import kr.or.connect.reservation.config.PasswordEncoder;
import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.member.dao.MemberDao;
import kr.or.connect.reservation.domain.member.dto.Member;
import kr.or.connect.reservation.domain.member.dto.MemberRequest;
import kr.or.connect.reservation.domain.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import static kr.or.connect.reservation.utils.UtilConstant.DEFAULT_TOTAL_RESERVATION_COUNT;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao userDao;
    private final PasswordEncoder pwEncoder;

    public Member login(String email, String password) {
        Member user = userDao.findMemberByEmail(email)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));

        String encodePwd = user.getPassword();
        if (!pwEncoder.matches(password, encodePwd)) {
            throw new CustomException(CustomExceptionStatus.USER_LOGIN_FAIL);
        }

        return user;
    }

    @Transactional
    public MemberResponse join(MemberRequest userRequest) {
        // 이미 가입된 이메일 검사
        String email = userRequest.getEmail();
        Optional<Member> joinedUser = userDao.findMemberByEmail(email);
        if (joinedUser.isPresent()) {
            throw new CustomException(CustomExceptionStatus.DUPLICATE_USER_EMAIL);
        }

        String encodePwd = null;
        try {
            encodePwd = pwEncoder.encode(userRequest.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("잘못된 암호화 알고리즘 입니다.");
        }
        Member user = convertUser(userRequest, encodePwd);

        int id = userDao.saveMember(user);
        return convertUserResponse(user, id);
    }

    private Member convertUser(MemberRequest userRequest, String encodePwd) {
        Member joinUser = new Member();
        joinUser.setName(userRequest.getName());
        joinUser.setEmail(userRequest.getEmail());
        joinUser.setPassword(encodePwd);
        joinUser.setTotalReservationCount(DEFAULT_TOTAL_RESERVATION_COUNT);
        joinUser.setCreateDate(LocalDateTime.now());
        joinUser.setModifyDate(LocalDateTime.now());
        return joinUser;
    }

    private MemberResponse convertUserResponse(Member user, int id) {
        MemberResponse newUserRequest = new MemberResponse();
        newUserRequest.setId(id);
        newUserRequest.setEmail(user.getEmail());
        newUserRequest.setName(user.getName());
        return newUserRequest;
    }

    private Integer getUserTotalReservationCount(Integer id) {
        Integer totalReservationCount = userDao.findMemberTotalReservationCountById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_FOUND));
        return totalReservationCount;
    }
}
