package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 엔터티를 직접 생성하는 것을 막을 수 있음
public class Order {
    // orders 연관관계의 주인 (FK: member) FK 값을 변경해야 Member테이블(연관관계 주인이 아니라면, 읽기전용) 값도 변경
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY) // 오더와 멤버는 다대일 관계
    @JoinColumn(name="member_id") // FK
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 일대일 관계에서는 FK는 액세스 많이 하는 곳에 두기(가급적)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // enum, 주문상태 [ORDER, CANCEL]

    // ==연관 관계 메서드== : 연관관계의 주인 쪽에 작성//
    public void setMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this) ;
    }

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now()); // 현재 시각 설정
        return order;
    }

    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) { // 주문 취소 불가능 (배송 완료인 상태)
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능합니다.");
        }
        // 주문 취소 가능한 상태
        this.setStatus(OrderStatus.CANCEL);

        // 주문 취소되는 경우 재고도 원복
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //== 조회 로직 ==//
    public int getTotalPrice() { // 전체 주문 가격 조회
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice(); // 주문 item 의 가격 * 주문 수량
        }
        return totalPrice;
    }
}
