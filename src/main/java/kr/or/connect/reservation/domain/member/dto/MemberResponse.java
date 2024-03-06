package kr.or.connect.reservation.domain.member.dto;

import kr.or.connect.reservation.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String name;
    private String email;

    public MemberResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }
}
