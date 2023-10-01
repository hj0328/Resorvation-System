package kr.or.connect.reservation.domain.user.dao;

import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserGrade;

import java.util.Optional;

public interface UserDao {

    int saveUser(User user);

    Optional<User> findUserByEmail(String email);

    Optional<UserGrade> findUserTypeById(Integer id);

    Optional<Integer> findUserTotalReservationCountById(Integer id);

    int updateTypeAndTotalReservationCountById(Integer userId, UserGrade userGrade
            , Integer totalReservationCount);
}
