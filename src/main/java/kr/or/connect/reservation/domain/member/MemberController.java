package kr.or.connect.reservation.domain.member;

import kr.or.connect.reservation.domain.member.dto.Member;
import kr.or.connect.reservation.domain.member.dto.MemberRequest;
import kr.or.connect.reservation.domain.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static kr.or.connect.reservation.utils.UtilConstant.USER_EMAIL;
import static kr.or.connect.reservation.utils.UtilConstant.USER_ID;

@Slf4j
@Controller
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     * 이름, 이메일, 비밀번호 모두 필수
     */
    @PostMapping
    public MemberResponse join(@Valid @RequestBody MemberRequest memberRequest) {
        return memberService.join(memberRequest);
    }

    /**
     * 로그인
     * 이름, 이메일, 비밀번호 모두 필수
     * 로그인 성공 시 세션 유지
     */
    @PostMapping("/session")
    public MemberResponse login(HttpServletRequest request, @Valid @RequestBody MemberRequest memberRequest) {
        MemberResponse userResponse = new MemberResponse();
        Member member = memberService.login(memberRequest.getEmail(), memberRequest.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(USER_EMAIL, member.getEmail());
        session.setAttribute(USER_ID, member.getId());

        userResponse.setId(member.getId());
        userResponse.setName(member.getName());
        userResponse.setEmail(member.getEmail());
        return userResponse;
    }

    /**
     * 로그아웃
     * 세션 삭제
     */
    @DeleteMapping("/session")
    public Map<String, String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Map<String, String> map = new HashMap<>();
        map.put(USER_EMAIL, "expired");
        return map;
    }

}
