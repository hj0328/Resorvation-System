package kr.or.connect.reservation.domain.member.dao;

import kr.or.connect.reservation.domain.member.dto.MemberDto;
import kr.or.connect.reservation.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryJdbcTemplateTest {

    @Autowired
    private MemberDao repository;
    private Member user;
    private final String TEST_EMAIL = "USER_TEST_EMAIL@gmail.com";

    @BeforeEach
    void init() {
        user = Member.create(TEST_EMAIL, "lee", "test");
    }

    @Test
    void saveUserTest() {
        // when
        int cnt = repository.saveMember(user);

        // then
        assertThat(cnt).isEqualTo(1);
    }

    @Test
    void findUserByEmailTest() {
        // given
        repository.saveMember(user);

        // when
        MemberDto userA = repository.findMemberByEmail(TEST_EMAIL).get();

        // then
        assertThat(userA.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findUserByEmailFailTest() {
        // when
        Optional<MemberDto> userA = repository.findMemberByEmail(TEST_EMAIL);

        // then
        assertThat(userA).isEqualTo(Optional.empty());
    }

    @Test
    void findUserTotalReservationCountByIdTest() {
        // given
        repository.saveMember(user);

        // when
        Integer count = repository.findMemberTotalReservationCountById(1).get();

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    void findUserTotalReservationCountByIdFailTest() {
        // when
        Optional<Integer> count = repository.findMemberTotalReservationCountById(1);

        // then
        assertThat(count).isEqualTo(Optional.empty());
    }

}