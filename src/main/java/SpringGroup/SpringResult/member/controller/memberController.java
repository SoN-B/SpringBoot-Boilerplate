package SpringGroup.SpringResult.member.controller;

import SpringGroup.SpringResult.domain.member;
import SpringGroup.SpringResult.repository.memoryMemberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class memberController {

  // final: 한 번 할당되면 변경할 수 없는 상수를 선언
  // 즉, repository는 생성자에서 한 번 초기화되고 나면 변경되지 않습니다.
  private final memoryMemberRepository repository;

  /**
   * memberController의 생성자가 memoryMemberRepository 타입의 빈을 필요로 합니다.
   * Spring IoC 컨테이너에서 해당 타입의 빈을 찾을 수 있게 해야 합니다.
   * (@Repository 어노테이션을 통해 빈으로 등록)
   */
  public memberController(memoryMemberRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public member createMember(@RequestBody member member) {
    return repository.save(member);
  }

  @GetMapping("/{id}")
  public Optional<member> getMember(@PathVariable Long id) {
    return repository.findById(id);
  }

  @GetMapping
  public List<member> getAllMembers() {
    return repository.findAll();
  }
}