package SpringGroup.SpringResult.domain.member.repository;

import java.util.List;
import java.util.Optional;

import SpringGroup.SpringResult.domain.member.model.Member;

public interface MemberRepository {
  Member save(Member member); // save: 회원이 저장소에 저장

  /**
   * Optional: 메소드가 member 객체를 반환할 수도 있지만,
   * 찾는 id에 해당하는 member가 없을 경우 null을 반환할 수도 있음
   */
  Optional<Member> findById(Long id); // findById: 저장소에서 id를 찾아 member 객체 반환

  Optional<Member> findByName(String name); // findByName: 저장소에서 name을 찿아 member 객체 반환

  /**
   * List: Java의 인터페이스 중 하나로, 순서가 있는 컬렉션을 의미합니다.
   * List는 중복된 요소를 허용하며, 각 요소는 인덱스에 의해 접근할 수 있습니다.
   */
  List<Member> findAll(); // findAll: 저장된 모든 회원의 member List 반환
}