package SpringGroup.SpringResult.domain.member.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;

@Builder
@Data
@Entity // 엔티티 정의
@Table(name = "member_table") // 사용하지 않으면 클래스 이름이 테이블 이름이 됨
@NoArgsConstructor
@AllArgsConstructor
public class MemberJpa implements UserDetails {
  @Id // 기본키를 의미. 반드시 기본키를 가져야함.
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Column(length = 50)
  private String email;

  @NotEmpty
  @Column(length = 100)
  private String password;

  @NotEmpty
  @Column(unique = true, length = 10) // 유일하고 최대 길이가 10.
  private String name;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "role")
  @Builder.Default
  private List<String> roles = new ArrayList<>();

  // UserDetails의 메서드 구현 (getAuthorities을 오버라이딩 하는 부분)
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return email;
  }

  // 계정이 활성화 되어있는지 (true: 활성화)
  @Override
  public boolean isEnabled() {
    return true;
  }

  // 계정의 비밀번호가 만료되지 않았는지 (true: 인증 정보가 유효함)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  // 계정이 만료되지 않았는지 (true: 계정이 유효함)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // 계정이 잠겨있지 않은지 (true: 잠기지 않음)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  public MemberJpa(String email, String name, String password, List<String> roles) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.roles = roles;
  }
}