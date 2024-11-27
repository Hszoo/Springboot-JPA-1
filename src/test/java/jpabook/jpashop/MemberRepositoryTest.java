package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // Junit에 Spring과 관련된 테스트를 하겠다고 알림
@SpringBootTest // Springboot 로 테스트 하겠다.
class MemberRepositoryTest {

//    // MemberRepository를 테스트 할거니까 주입
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    public void memberTest() throws Exception {
//        // given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // when
//        Long saveId = memberRepository.save(member); // 저장했을 때
//        Member findMember = memberRepository.find(saveId);
//
//        // then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId()); // 잘 저장되었는지 비교 두 객체 비교
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//
//        // 두 객체 자체를 비교 -> 결과 : true
//        // 같은 Transaction 에서 실행 -> 영속성 context 동일 -> id값이 같으면, 같은 객체로 인식됨
//        Assertions.assertThat(findMember).isEqualTo(member);
//        System.out.println("findMember == member " + (findMember == member));
//
//    }

}