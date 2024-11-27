package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
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
}
