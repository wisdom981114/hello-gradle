package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public class MemberService {

    private MemberRepository memberRepository;


    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }


    public Long join(Member member){

        validateDuplicateMember(member); // 중복회원 검증
        //System.out.println("~~~ MemberService join save 직전");
        memberRepository.save(member);
        //System.out.println("~~~ MemberService join save 직후");
        return member.getId();

    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("~~~ 이미 존재하는 회원입니다. validateDuplicateMember");
                });
    }

//    private void validateDuplicateMember(Member member) {
//        memberRepository.findByName(member.getName()) //cnt alt v
//                //Optinal은 값이 존재하지 않을 수 있는 객체를 나타내는
//            .ifPresent(m -> {
//                //ifPresent 메소드는 람다 표현식을 매개변수로 받으며
//                // Optional 객체에 값이 존재할 경우 해당 람다 표현식이 실행됩니다
//                //만약 Optional 객체에 값이 존재하지 않을 경우, 아무 동작도 수행하지 않습니다.
//                throw new IllegalStateException("이미 존재하는 회원입니다.");
//                // IllegalStateException은 예외중 하나로
//                //메서드나 객체의 상태가 적절하지 않을때 발생
//                //메서드가 호출될 때 해당 객체의 상태가 특정한 값을 가지지 않거나
//                //이전에 수행한 작업의 결과에 따라 호출될수 없는 상태일때 발생할수 있음
//        });
//    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public  Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
