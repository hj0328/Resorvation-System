package kr.or.connect.reservation.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {
    private Integer id;
    private String name;
    private String email;
}
