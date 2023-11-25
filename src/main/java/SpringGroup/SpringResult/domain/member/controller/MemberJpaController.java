package SpringGroup.SpringResult.domain.member.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import SpringGroup.SpringResult.domain.member.model.MemberJpa;
import SpringGroup.SpringResult.domain.member.repository.MemberJpaRepository;
import SpringGroup.SpringResult.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberJpaController {

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberJpaRepository repository;

  // 회원가입
  @PostMapping
  public MemberJpa createMember(@RequestBody MemberJpa member) {
    return repository.save(member);
  }

  // 로그인
  @PostMapping("/login")
  public String login(@RequestBody MemberJpa member) {
    MemberJpa memberJpa = repository.findByEmail(member.getUsername());
    if (memberJpa == null) {
      return "회원이 존재하지 않습니다.";
    } else if (!memberJpa.getPassword().equals(member.getPassword())) {
      return "비밀번호가 일치하지 않습니다.";
    } else {
      return jwtTokenProvider.createToken(memberJpa.getEmail(), memberJpa.getRoles());
    }
  }

  @GetMapping("/{id}")
  public MemberJpa getMember(@PathVariable Long id) {
    return repository.findById(id).orElse(null);
    // orElse(null): 값이 없을 때 null을 반환
  }

  @GetMapping
  public List<MemberJpa> getAllMembers() {
    return repository.findAll();
  }

  @PutMapping("/{id}")
  public MemberJpa updateMember(@PathVariable Long id, @RequestBody MemberJpa newMember) {
    return repository.findById(id)
        .map(member -> {
          member.setName(newMember.getName());
          return repository.save(member);
        })
        .orElseGet(() -> {
          newMember.setId(id);
          return repository.save(newMember);
        });
    // orElseGet(): Optional 객체가 MemberJpa 객체를 가지고 있으면 그 객체를 반환하고,
    // 그렇지 않으면 괄호 안의 람다 표현식을 실행
  }

  @DeleteMapping("/{id}")
  public void deleteMember(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
