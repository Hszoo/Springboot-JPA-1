package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // annot: 데이터 변경 로직 하나의 트랜잭션 내 수행
// readOnly=True 조회 시 JPA가 성능 최적화 (이 서비스로직의 경우, 조회가 많아 클래스 레벨에서 readOnly로 설정)
public class MemberService {

    // @Autowired : Field Injection
    private final MemberRepository memberRepository; // 생성자에서 주입된 이후에는 변경되지 않아, final로

    // @Autowired : setter Injection
    // public void setMemberRepository(MemberRepository memberRepository) {
    //    this.memberRepository = memberRepository;
    // }

    // @Autowired : Constructor Injection (Springboot 최신버전에서는 생략 가능)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    /* 회원 가입 */
    @Transactional // 쓰기는 별도로 Transcational annot 추가
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증 : 동시에 같은 name의 회원이 회원가입 -> 검증을 뚫을 수 있음 (방어: DB에서 unique제약을 추가)
    private void validateDuplicateMember(Member member) {
        // 중복 회원 : exception
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) // 같은 이름을 갖는 회원이 존재하는 경우
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    /* 회원 전체 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 회원 1 조회 */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
