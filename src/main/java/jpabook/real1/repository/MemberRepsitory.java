package jpabook.real1.repository;

import jpabook.real1.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepsitory {

    //@PersistenceContext //이 어노테이션 사용하면 스프링 부트가 엔티티 매니저 주입
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); //커맨드와 쿼리를 분리해라
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }
}
