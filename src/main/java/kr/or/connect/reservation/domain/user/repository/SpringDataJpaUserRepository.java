package kr.or.connect.reservation.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaUserRepository extends JpaRepository<User, Integer> {
    User save(User user);

    User findByEmail(String email);

    List<TypeView> findUserTypeByEmail(String email);

    List<TotalReservationCountView> findTotalReservationCountByEmail(String email);

}
