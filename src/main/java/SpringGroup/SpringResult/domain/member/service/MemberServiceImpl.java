package SpringGroup.SpringResult.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SpringGroup.SpringResult.domain.member.dto.MemberDto.CreateMemberRequest;
import SpringGroup.SpringResult.domain.member.dto.MemberDto.GetMemberResponse;
import SpringGroup.SpringResult.domain.member.dto.MemberDto.UpdateMemberRequest;
import SpringGroup.SpringResult.domain.member.exception.MemberException;
import SpringGroup.SpringResult.domain.member.model.Member;
import SpringGroup.SpringResult.domain.member.repository.MemberRepository;
import SpringGroup.SpringResult.global.exception.ErrorCode;
import SpringGroup.SpringResult.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final JwtTokenProvider jwtTokenProvider;
  private final MemberRepository repository;

  @Override
  @Transactional(readOnly = true)
  public GetMemberResponse getMember(Long id) {
    Member member = repository.findById(id).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    return GetMemberResponse.from(member);
  }

  @Override
  @Transactional(readOnly = true)
  public List<GetMemberResponse> getAllMember() {
    List<Member> members = repository.findAll();

    if (members.isEmpty())
      throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);

    List<GetMemberResponse> memberInfos = members.stream()
        .map(GetMemberResponse::from)
        .collect(Collectors.toList());

    return memberInfos;
  }

  @Override
  @Transactional
  public void register(CreateMemberRequest memberInfo) {
    Member member = new Member(memberInfo.getEmail(), memberInfo.getName(), memberInfo.getPassword(),
        memberInfo.getRoles());
    repository.save(member);
  }

  @Override
  public String login(Member memberInfo) {
    Member member = repository.findByEmail(memberInfo.getUsername());
    return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
  }

  @Override
  @Transactional
  public void updateMember(Member currentMember, Long id, UpdateMemberRequest updateInfo) {
    if (!currentMember.getId().equals(id))
      throw new MemberException(ErrorCode.MEMBER_UNAUTHORIZED_UPDATE);

    Member member = repository.findById(id).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

    member.setName(updateInfo.getName());
    member.setEmail(updateInfo.getEmail());
    member.setPassword(updateInfo.getPassword());
    member.setRoles(updateInfo.getRoles());

    repository.save(member);
  }

  @Override
  @Transactional
  public void deleteMember(Member currentMember, Long id) {
    if (!currentMember.getId().equals(id))
      throw new MemberException(ErrorCode.MEMBER_UNAUTHORIZED_DELETE);

    repository.deleteById(id);
  }
}
