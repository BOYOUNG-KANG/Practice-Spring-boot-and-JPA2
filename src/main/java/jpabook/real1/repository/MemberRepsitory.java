package jpabook.real1.repository;

import jpabook.real1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepsitory extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);
}
