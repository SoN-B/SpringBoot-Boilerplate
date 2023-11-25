package SpringGroup.SpringResult.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringGroup.SpringResult.domain.member.model.MemberJpa;

public interface MemberJpaRepository extends JpaRepository<MemberJpa, Long> {
  MemberJpa findByEmail(String email);
}