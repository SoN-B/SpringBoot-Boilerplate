package SpringGroup.SpringResult.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SpringGroup.SpringResult.domain.member.dto.MemberDto.UpdateMemberRequest;
import SpringGroup.SpringResult.domain.member.exception.MemberException;
import SpringGroup.SpringResult.domain.member.model.Member;
import SpringGroup.SpringResult.domain.member.repository.MemberRepository;
import SpringGroup.SpringResult.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServicelmpl implements MemberService {
  private final MemberRepository repository;

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
}
