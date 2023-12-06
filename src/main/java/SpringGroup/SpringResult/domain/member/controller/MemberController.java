package SpringGroup.SpringResult.domain.member.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import SpringGroup.SpringResult.global.response.*;
import SpringGroup.SpringResult.domain.member.dto.MemberDto.*;
import SpringGroup.SpringResult.domain.member.model.Member;
import SpringGroup.SpringResult.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  /**
   * 회원 가입
   *
   * @param memberInfo 사용자가 입력한 회원 정보 (이름, 이메일, 비밀번호)
   */
  @PostMapping
  public ResponseEntity<Response> register(@RequestBody @Valid CreateMemberRequest memberInfo) {
    memberService.register(memberInfo);
    return ResponseEntity.ok(Response.success("Member registered."));
  }

  /**
   * 로그인
   *
   * @param memberInfo 사용자가 입력한 회원 정보 (이메일, 비밀번호)
   */
  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody Member memberInfo) {
    String token = memberService.login(memberInfo);
    return ResponseEntity.ok(Response.success(token, "Login success."));
  }

  /**
   * 특정 회원 정보 조회
   *
   * @param id 조회할 회원의 id
   *
   * @exception 1. 조회하려는 회원 정보가 없을 경우
   */
  @GetMapping("/{id}")
  public ResponseEntity<Response> getMember(@PathVariable Long id) {
    GetMemberResponse memberInfo = memberService.getMember(id);
    return ResponseEntity.ok(Response.success(memberInfo));
  }

  /**
   * 모든 회원 정보 조회
   *
   * @exception 1. 회원 정보가 없을 경우
   */
  @GetMapping
  public ResponseEntity<Response> getAllMember() {
    List<GetMemberResponse> memberInfos = memberService.getAllMember();
    return ResponseEntity.ok(Response.success(memberInfos));
  }

  /**
   * 회원 정보 수정 (자신의 정보만 수정 가능)
   *
   * @param currentMember 현재 로그인한 회원 정보
   * @param id            수정할 회원의 id
   * @param updateInfo    수정할 회원 정보 (이름, 이메일, 비밀번호, 권한)
   *
   * @exception 1. 로그인한 회원과 수정하려는 회원 정보가 다를 경우
   */
  @PutMapping("/{id}")
  public ResponseEntity<Response> updateMember(@AuthenticationPrincipal Member currentMember, @PathVariable Long id,
      @RequestBody @Valid UpdateMemberRequest updateInfo) {
    memberService.updateMember(currentMember, id, updateInfo);
    return ResponseEntity.ok(Response.success("Member updated."));
  }

  /**
   * 회원 정보 삭제 (자신의 정보만 삭제 가능)
   *
   * @param currentMember 현재 로그인한 회원 정보
   * @param id            삭제할 회원의 id
   *
   * @exception 1. 로그인한 회원과 삭제하려는 회원 정보가 다를 경우
   */
  @DeleteMapping("/{id}")
  public void deleteMember(@AuthenticationPrincipal Member currentMember, @PathVariable Long id) {
    memberService.deleteMember(currentMember, id);
  }
}
