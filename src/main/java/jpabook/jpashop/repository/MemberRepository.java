package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import jpabook.jpashop.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext : Spring에서 EntityManager를 만든 후 주입
    private final EntityManager em;

    // jpa를 이용한 객체 저장
    public void save(Member member) {
        // jpa가 member를 저장
        em.persist(member);
    }

    // jpa를 이용한 객체 조회
    public Member findOne(Long id) {
        // jpa 제공 메서드로 id에 해당하는 객체를 찾아 return
        return em.find(Member.class, id);
    }

    // jpa를 이용한 객체 list 조회 by. JPQL
    public List<Member> findAll() {
        // param1: JPQL, param2: 반환타입

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        // JPQL의 파라미터 바인딩 (by. setParameter())
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }




}
