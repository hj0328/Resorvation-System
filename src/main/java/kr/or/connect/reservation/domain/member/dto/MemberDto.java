package kr.or.connect.reservation.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberDto {
    private Integer id;
    private String email;
    private String name;
    private String password;

    private MemberDto(Integer id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }
}