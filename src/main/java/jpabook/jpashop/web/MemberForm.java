package jpabook.jpashop.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message="회원 이름은 필수 입니다.") // 필수 입력
    private String name;

    private String street;
    private String city;

    private String zipcode;
}
