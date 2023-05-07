package jpabook.real1.repository;

import jpabook.real1.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //junit5에서 runwith 대신 사용
@SpringBootTest
class MemberRepsitoryTest {

    @Autowired MemberRepsitory memberRepsitory;

    @Test
    @Transactional //스프링이 제공하는걸로 사용
    @Rollback(false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepsitory.save(member);
        Member findMember = memberRepsitory.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember = member : " + (findMember == member)); //true
        //같은 트랜잭션 안에서 저장, 조회 시 영컨이 같기 때문
        //영컨에서 식별자 같으면 같은 엔티티로 봄
    }

    @Test
    void findId() {
    }
}