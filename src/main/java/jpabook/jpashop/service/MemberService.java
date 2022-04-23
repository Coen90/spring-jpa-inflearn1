package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 데이터 변경 -> 트렌젝션 안에서 이루어져야 함
// @AllArgsConstructor // 생성자 만들어줌
@RequiredArgsConstructor // 파이널 가지고있는 아이만 생성자 만들어줌 injection way2
public class MemberService {

    // injection way1
//    @Autowired
//    private MemberRepository memberRepository; // Autowired는 변경 불가

    private final MemberRepository memberRepository; // final 을 넣으면 인젝션 안했을때 빨간불 들어와서 권장함

    // injection way2
//    @Autowired // 생성자가 하나인경우 오토 인젝션함
//    public MemberService(MemberRepository memberRepository) { // 생성자 인젝션은 실행 이후 set으로 변경 불가하기에 더 안전함
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMember = memberRepository.findByName(member.getName());
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true) // 읽기전용 (조회하는데 성능최적화, 데이터 변경 불가)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
