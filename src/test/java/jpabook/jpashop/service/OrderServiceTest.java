package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus()); // 메시지, 기대값, 실제값
        assertEquals("주문한 상품 주문의 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
     }

    @Test(expected = NotEnoughStockException.class)
     public void 상품주문_재고수량초과() throws Exception {
         // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); // 재고가 10개

        int orderCount = 11 ; // 주문 수량이 11개라면, 초과 주문 -> 예외 발생

         // when
        orderService.order(member.getId(), item.getId(), orderCount); // 여기서 예외 발생해서 메서드 종료되어야 테스트 성공

         // then
        fail("재고 수량 부족 예외가 발생해야 한다."); // 여기까지 실행된다면 테스트 실패
      }

     @Test
     public void 주문취소() throws Exception {
         // given
         Member member = createMember();
         Item item = createBook("시골JPA", 10000, 10);

         int orderCount = 2;
         Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

         // when : 실제 테스트할 부분
         orderService.cancelOrder(orderId);

         // then
         Order getOrder = orderRepository.findOne(orderId);

         assertEquals("주문 취소 시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
         assertEquals("주문이 취소된 상품의 재고는 증가해야 한다.", 10, item.getStockQuantity());
     }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("테스트");
        member.setAddress(new Address("서울", "목동", "123-123"));
        em.persist(member);
        return member;
    }
}