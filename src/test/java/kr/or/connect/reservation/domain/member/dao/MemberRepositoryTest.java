package kr.or.connect.reservation.domain.member.dao;

import kr.or.connect.reservation.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test () throws Exception {
        // given
        Member member = Member.create("gmail.com", "lee", "pwd");
        Member save = memberRepository.saveAndFlush(member);
        // when

        Optional<Member> member1 = memberRepository.findMemberByEmail("gmail.com");
        System.out.println(member1.get().getEmail());
        // then
    }
}