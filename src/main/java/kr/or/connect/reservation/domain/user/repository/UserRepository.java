package kr.or.connect.reservation.domain.user.repository;

import kr.or.connect.reservation.domain.user.UserType;

public interface UserRepository {
    Object save(User userDto);

    User findByEmail(String email);

    Integer findTotalReservationCountByEmail(String email);

    UserType findTypeByEmail(String email);

    void update(String email, UserType userType
            , Integer totalReservationCount);
}
