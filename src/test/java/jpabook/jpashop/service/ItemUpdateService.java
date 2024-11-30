package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateService {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {

        Book book = em.find(Book.class, 1L);

        // 트랜잭션 내 데이터 변경
        book.setName("이름수정");

        // 변경감지(Dirty Checking) : JPA가 데이터 변경 확인 -> update 쿼리 작성 & 반영

        // 트랜잭션 commit -> DB 반영됨

     }
}
