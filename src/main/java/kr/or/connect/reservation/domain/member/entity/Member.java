package kr.or.connect.reservation.domain.member.entity;

import kr.or.connect.reservation.domain.BaseEntity;
import kr.or.connect.reservation.domain.reservation.entity.Reservation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member")
    private final List<Reservation> reservationList = new ArrayList<>();

    private String email;
    private String name;
    private String password;

    private Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static Member create(String email, String name, String password) {
        return new Member(email, name, password);
    }
}
