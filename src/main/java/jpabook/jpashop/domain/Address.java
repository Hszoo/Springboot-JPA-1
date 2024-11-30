package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 내장타입
@Getter
public class Address { // 값 타입은 변경 불가능하게 설계 -> Setter X
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipCocde) { // 값의 초기화도 생성자를 통해서
        this.city = city;
        this.street = street;
        this.zipcode = zipCocde;
    }
}
