package kr.or.connect.reservation.domain.user.dao;

import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryJdbcTemplateTest {

    @Autowired
    private UserDaoJdbcTemplate repository;
    private User user;
    private final String TEST_EMAIL = "USER_TEST_EMAIL@gmail.com";

    @BeforeEach
    void init() {
        user = new User();
        user.setId(1);
        user.setEmail(TEST_EMAIL);
        user.setPassword("test");
        user.setName("lee");
        user.setGrade(UserGrade.BASIC);
        user.setTotalReservationCount(1);
    }

    @Test
    void saveUserTest() {
        // when
        int cnt = repository.saveUser(user);

        // then
        assertThat(cnt).isEqualTo(1);
    }

    @Test
    void findUserByEmailTest() {
        // given
        repository.saveUser(user);

        // when
        User userA = repository.findUserByEmail(TEST_EMAIL).get();

        // then
        assertThat(userA.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findUserByEmailFailTest() {
        // when
        Optional<User> userA = repository.findUserByEmail(TEST_EMAIL);

        // then
        assertThat(userA).isEqualTo(Optional.empty());
    }

    @Test
    void findUserTypeByIdTest() {
        // given
        repository.saveUser(user);

        // when
        UserGrade userGrade = repository.findUserTypeById(1).get();

        // then
        assertThat(userGrade).isEqualTo(UserGrade.BASIC);
    }

    @Test
    void findUserTypeByIdFailTest() {
        // when
        Optional<UserGrade> userA = repository.findUserTypeById(1);

        // then
        assertThat(userA).isEqualTo(Optional.empty());
    }

    @Test
    void findUserTotalReservationCountByIdTest() {
        // given
        repository.saveUser(user);

        // when
        Integer count = repository.findUserTotalReservationCountById(1).get();

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    void findUserTotalReservationCountByIdFailTest() {
        // when
        Optional<Integer> count = repository.findUserTotalReservationCountById(1);

        // then
        assertThat(count).isEqualTo(Optional.empty());
    }

    @Test
    void updateTypeAndTotalReservationCountByIdTest() {
        // given
        repository.saveUser(user);

        // when
        Integer count = repository.updateTypeAndTotalReservationCountById(1, UserGrade.VIP, 11);

        // then
        assertThat(count).isEqualTo(1);

        User updateUser = repository.findUserByEmail(user.getEmail()).get();
        assertThat(updateUser.getGrade()).isEqualTo(UserGrade.VIP);
    }

}