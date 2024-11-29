package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY) // 오더와 오더아이템은 일대다관계
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice; // 주문 당시 가격
    private int count; // 주문 수량

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문이 들어온 만큼 재고 수량 --
        return orderItem;
    }

    //== 비즈니스 로직 ==//
    // 고객이 주문을 취소한 경우 재고도 원복
    public void cancel() {
        getItem().addStock(count); // 고객이 주문했던 수량만큼 재고 증가시킴
    }

    //== 조회 로직 ==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
