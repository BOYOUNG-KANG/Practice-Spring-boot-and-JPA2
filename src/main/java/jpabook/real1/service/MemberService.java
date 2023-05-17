package jpabook.real1.service;

import jpabook.real1.domain.Member;
import jpabook.real1.repository.MemberRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //데이터 변경은 트랜잭션 안에서
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepsitory memberRepsitory;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validationDuplicateMember(member); //중복회원 검증
        memberRepsitory.save(member);
        return member.getId();
    }

    private void validationDuplicateMember(Member member) {
        List<Member> findMembers = memberRepsitory.findByName(member.getUsername());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepsitory.findAll();
    }
    public Member findMember(Long id) {

        return memberRepsitory.findOne(id);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepsitory.findOne(id);
        member.setUsername(name);
    }
}
