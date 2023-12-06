package SpringGroup.SpringResult.domain.member.service;

import java.util.List;

import SpringGroup.SpringResult.domain.member.dto.MemberDto.CreateMemberRequest;
import SpringGroup.SpringResult.domain.member.dto.MemberDto.UpdateMemberRequest;
import SpringGroup.SpringResult.domain.member.dto.MemberDto.GetMemberResponse;
import SpringGroup.SpringResult.domain.member.model.Member;

public interface MemberService {
  GetMemberResponse getMember(Long id);

  List<GetMemberResponse> getAllMember();

  void register(CreateMemberRequest memberInfo);

  String login(Member memberInfo);

  void updateMember(Member currentMember, Long id, UpdateMemberRequest updateInfo);

  void deleteMember(Member currentMember, Long id);
}
