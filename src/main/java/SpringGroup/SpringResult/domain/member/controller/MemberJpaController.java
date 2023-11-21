package SpringGroup.SpringResult.domain.member.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import SpringGroup.SpringResult.domain.member.model.MemberJpa;
import SpringGroup.SpringResult.domain.member.repository.MemberJpaRepository;

@RestController
@RequestMapping("/members")
public class MemberJpaController {
  private final MemberJpaRepository repository;

  public MemberJpaController(MemberJpaRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public MemberJpa createMember(@RequestBody MemberJpa member) {
    return repository.save(member);
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
