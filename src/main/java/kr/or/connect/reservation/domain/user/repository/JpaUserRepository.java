package kr.or.connect.reservation.domain.user.repository;

import kr.or.connect.reservation.domain.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final SpringDataJpaUserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Integer findTotalReservationCountByEmail(String email) {
        Integer totalReservationCountByEmail = repository.findTotalReservationCountByEmail(email)
                .get(0)
                .getTotalReservationCount();
        return totalReservationCountByEmail;
    }


    @Override
    public UserType findTypeByEmail(String email) {
        UserType userTypeByEmail = repository.findUserTypeByEmail(email).get(0).getType();
        return userTypeByEmail;
    }

    @Override
    public void update(String email, UserType userType
            , Integer totalReservationCount) {
        User user = repository.findByEmail(email);
        user.setType(userType);
        user.setTotalReservationCount(totalReservationCount);
    }
}

