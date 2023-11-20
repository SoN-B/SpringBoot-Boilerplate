package SpringGroup.SpringResult.domain.member.service;

import SpringGroup.SpringResult.domain.member.model.Member;
import SpringGroup.SpringResult.domain.member.repository.MemberRepository;
import SpringGroup.SpringResult.domain.member.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
  // 저장소
  private final MemberRepository memberRepository = new MemoryMemberRepository();

  // 회원가입
  public Member join(Member member) {

    // 중복회원 검증 메서드 호출
    validateDuplicateMember(member);

    // member를 저장소에 저장
    memberRepository.save(member);
    // member id 반환
    return member;
  }

  // 중복회원 검증 메서드
  private void validateDuplicateMember(Member member) {
    memberRepository.findByName(member.getName())
        .ifPresent(m -> { // 이름이 같은 회원이 존재하면
          throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
  }

  // 전체 회원 조회
  public List<Member> findAll() {
    return memberRepository.findAll();
  }

  // id로 회원 조회
  public Optional<Member> findById(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
