package jpabook.real1.service;

import jpabook.real1.domain.Member;
import jpabook.real1.repository.MemberRepsitory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService; //테스트케이스니깐 간단하게
    @Autowired MemberRepsitory memberRepsitory;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("강보영");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepsitory.findOne(saveId));

    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setUsername("kim");
        Member member2 = new Member();
        member2.setUsername("kim");

        //when
        memberService.join(member1);

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2)); //junit5 예외처리
    }
}