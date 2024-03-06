package kr.or.connect.reservation.domain.member;

import kr.or.connect.reservation.config.PasswordEncoder;
import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.member.dao.MemberRepository;
import kr.or.connect.reservation.domain.member.dto.MemberRequest;
import kr.or.connect.reservation.domain.member.dto.MemberResponse;
import kr.or.connect.reservation.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder pwEncoder;

    public MemberResponse login(String email, String password) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MEMBER_NOT_FOUND));

        String encodePwd = member.getPassword();
        if (!pwEncoder.matches(password, encodePwd)) {
            throw new CustomException(CustomExceptionStatus.MEMBER_LOGIN_FAIL);
        }

        return MemberResponse.of(member);
    }

    @Transactional
    public MemberResponse join(MemberRequest memberRequest) {
        String email = memberRequest.getEmail();
        Optional<Member> joinedMember = memberRepository.findMemberByEmail(email);
        if (joinedMember.isPresent()) {
            throw new CustomException(CustomExceptionStatus.DUPLICATE_MEMBER_EMAIL);
        }

        try {
            String encodePwd = pwEncoder.encode(memberRequest.getPassword());
            Member member = Member.create(
                    memberRequest.getEmail(), memberRequest.getName(), encodePwd);

            Member saveMember = memberRepository.save(member);
            return MemberResponse.of(saveMember);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("잘못된 암호화 알고리즘 입니다.");
        }
    }
}
