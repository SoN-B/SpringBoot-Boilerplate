package SpringGroup.SpringResult.domain.member.service;

import SpringGroup.SpringResult.domain.member.dto.MemberDto.UpdateMemberRequest;
import SpringGroup.SpringResult.domain.member.model.Member;

public interface MemberService {
  void updateMember(Member currentMember, Long id, UpdateMemberRequest updateInfo);
}
