package kr.or.connect.reservation.domain.user.repository;

import kr.or.connect.reservation.domain.user.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
@Getter @Setter
public class User {

    public User() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String email;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type;
    private Integer totalReservationCount;
}
