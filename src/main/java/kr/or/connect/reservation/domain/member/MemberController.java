package kr.or.connect.reservation.domain.member;

import kr.or.connect.reservation.domain.member.dto.MemberRequest;
import kr.or.connect.reservation.domain.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static kr.or.connect.reservation.utils.UtilConstant.MEMBER_ID;
import static kr.or.connect.reservation.utils.UtilConstant.USER_EMAIL;

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
    public ResponseEntity<MemberResponse> join(@Valid @RequestBody MemberRequest memberRequest) {
        MemberResponse response = memberService.join(memberRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * 로그인
     * 이메일, 비밀번호 모두 필수
     * 로그인 성공 시 세션 유지
     */
    @PostMapping("/session")
    public ResponseEntity<MemberResponse> login(HttpSession session
            , @Valid @RequestBody MemberRequest memberRequest) {
        MemberResponse memberResp = memberService.login(memberRequest.getEmail(), memberRequest.getPassword());

        session.setAttribute(USER_EMAIL, memberResp.getEmail());
        session.setAttribute(MEMBER_ID, memberResp.getId());
        session.setMaxInactiveInterval(1800);

        return ResponseEntity.ok(memberResp);
    }

    /**
     * 로그아웃
     * 세션 삭제
     */
    @DeleteMapping("/session")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }
}
