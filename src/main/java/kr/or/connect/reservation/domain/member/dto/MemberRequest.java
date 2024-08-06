package kr.or.connect.reservation.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class MemberRequest {
    private String name;

    @NotBlank(message = "이메일을 입력해주시기 바랍니다.")
    @Email(message = "이메일 형식을 확인바랍니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주시기 바랍니다.")
    private String password;

    private MemberRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    static public MemberRequest createMemberRequest(String name, String email, String password) {
        return new MemberRequest(name, email, password);
    }

}
