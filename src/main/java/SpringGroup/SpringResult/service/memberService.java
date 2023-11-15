package SpringGroup.SpringResult.service;

import SpringGroup.SpringResult.domain.member;
import SpringGroup.SpringResult.repository.memberRepository;
import SpringGroup.SpringResult.repository.memoryMemberRepository;

import org.springframework.stereotype.Service;

@Service
public class memberService {
  // 저장소
  private final memberRepository memberRepository = new memoryMemberRepository();

  // 회원가입
  public member join(member member) {

    // 중복회원 검증 메서드 호출
    validateDuplicateMember(member);

    // member를 저장소에 저장
    memberRepository.save(member);
    // member id 반환
    return member;
  }

  // 중복회원 검증 메서드
  private void validateDuplicateMember(member member) {
    memberRepository.findByName(member.getName())
        .ifPresent(m -> { // 이름이 같은 회원이 존재하면
          throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
  }
}
