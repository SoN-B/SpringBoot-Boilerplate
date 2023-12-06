package SpringGroup.SpringResult.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringGroup.SpringResult.domain.member.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Member findByEmail(String email);

  boolean existsByEmail(String email);
}