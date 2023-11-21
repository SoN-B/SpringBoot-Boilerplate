package SpringGroup.SpringResult.domain.member.model;

import javax.persistence.*;
import lombok.*;

@Entity // 엔티티 정의
@Table(name = "member_table") // 사용하지 않으면 클래스 이름이 테이블 이름이 됨
@Getter
@Setter
public class MemberJpa {
  @Id // 기본키를 의미. 반드시 기본키를 가져야함.
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @Column(unique = true, length = 10) // 유일하고 최대 길이가 10.
  private String name;
  // @Column(name = "member_age")
  // private int age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}