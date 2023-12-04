package SpringGroup.SpringResult.domain.member.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import SpringGroup.SpringResult.global.response.*;
import SpringGroup.SpringResult.domain.member.dto.MemberDto.*;
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
  public ResponseEntity<Response> createMember(@RequestBody @Valid CreateMemberRequest member) {
    MemberJpa memberInfo = new MemberJpa(member.getEmail(), member.getName(), member.getPassword(), member.getRoles());

    repository.save(memberInfo);
    return ResponseEntity.ok(Response.success("Member created."));
  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody MemberJpa member) {
    MemberJpa memberJpa = repository.findByEmail(member.getUsername());

    String token = jwtTokenProvider.createToken(memberJpa.getEmail(), memberJpa.getRoles());
    return ResponseEntity.ok(Response.success(token, "Login success."));
  }

  // 특정 회원 정보 조회
  @GetMapping("/{id}")
  public ResponseEntity<Response> getMember(@PathVariable Long id) {
    MemberJpa member = repository.findById(id).orElse(null); // orElse(null): 값이 없을 때 null을 반환

    MemberInfo memberInfo = MemberInfo.from(member);
    return ResponseEntity.ok(Response.success(memberInfo));
  }

  // 모든 회원 정보 조회
  @GetMapping
  public ResponseEntity<Response> getAllMembers() {
    List<MemberJpa> members = repository.findAll();

    List<MemberInfo> memberInfos = members.stream()
        .map(MemberInfo::from)
        .collect(Collectors.toList());
    return ResponseEntity.ok(Response.success(memberInfos));
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

  // 회원 정보 삭제 (자신의 정보만 삭제 가능)
  @DeleteMapping("/{id}")
  public void deleteMember(@AuthenticationPrincipal MemberJpa currentUser, @PathVariable Long id) {
    // 자신의 정보만 삭제 가능
    if (!currentUser.getId().equals(id))
      throw new AccessDeniedException("You can only delete your own information.");

    repository.deleteById(id);
  }
}
