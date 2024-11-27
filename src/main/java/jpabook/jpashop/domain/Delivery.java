package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy="delivery", fetch=FetchType.LAZY)
    private Order order;

    @Embedded // 내장타입
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // [READY, COMP-배송]

}
