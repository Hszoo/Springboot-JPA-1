package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) { // entity 저장 메서드
        em.persist(member);
        return member.getId(); // 저장 후에는 보통 return 값을 안만듦 (id 값정도 주면 됨)
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
