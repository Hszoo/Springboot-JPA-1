package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest // 두 annot으로 테스트 : Springboot 인티그레이션해서 테스트
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Hong");

        // when
        Long saveId = memberService.join(member);

        // then (생성한 멤버와, DB에 저장된 멤버의 id가 동일 -> 검증 완료)
        em.flush();
        Assert.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test()
    public void  중복_회원_예외() throws  Exception {
        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("hong1");
        member2.setName("hong1");

        // when
        memberService.join(member1);

        // when & then
        IllegalStateException exception = Assert.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        //  try - catch로 검증하자니 코드가 너무 지저분하다면 Test annot에서 expected로 대체 가능
//         try {
//             memberService.join(member2); // 검증 성공 = 예외 발생 : validateDuplicateMember() 메서드 -> Exception
//         } catch(IllegalStateException e) {
//            return;
//         }
        // try - catch 에서 예외 잡고 이하 코드는 실행되지 않아야 검증이 성공

        // then (여기부터 실행된다면 검증 실패)
        Assert.fail("예외가 발생해야 한다.");
    }
}