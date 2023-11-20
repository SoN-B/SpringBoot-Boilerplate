package SpringGroup.SpringResult.domain.member.controller;

import SpringGroup.SpringResult.domain.member.dto.MemberDto;
import SpringGroup.SpringResult.domain.member.model.Member;
import SpringGroup.SpringResult.domain.member.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {
  // final: 한 번 할당되면 변경할 수 없는 상수를 선언
  // 즉, repository는 생성자에서 한 번 초기화되고 나면 변경되지 않습니다.
  private final MemberService memberService;

  /**
   * memberController의 생성자가 memoryMemberRepository 타입의 빈을 필요로 합니다.
   * Spring IoC 컨테이너에서 해당 타입의 빈을 찾을 수 있게 해야 합니다.
   * (@Repository 어노테이션을 통해 빈으로 등록)
   */
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/page/create") // localhost::8080/member/page/create
  public String createForm() {
    return "member/create";
  }

  @GetMapping
  public String list(Model model) {
    List<Member> members = memberService.findAll(); // 회원 List 가져옴

    model.addAttribute("members", members); // 회원 List를 model에 넣음
    return "member/list";
  }

  @GetMapping("/{id}")
  @ResponseBody
  public Optional<Member> getMember(@PathVariable Long id) {
    return memberService.findById(id);
  }

  // Json 형태로 데이터를 받아옴
  // @PostMapping
  // public member createMember(@RequestBody member member) {
  // return memberService.join(member);
  // }

  @PostMapping
  public String create(MemberDto form) {
    Member member = new Member(); // member 객체 생성
    member.setName(form.getName()); // form에서 입력받은 이름을 member 객체 이름으로 넣음

    memberService.join(member); // member 객체로 join(회원가입)

    return "redirect:/"; // 바로 "localhost::8080/" 화면으로 이동
  }
}