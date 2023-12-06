package SpringGroup.SpringResult.domain.member.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import SpringGroup.SpringResult.domain.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberDto {
  private Long id;
  private String name;
  private String email;
  private String password;
  private List<String> roles;

  // 생성자 호출 시 예외 발생 (싱글턴 패턴: 클래스의 인스턴스를 하나만 생성하도록 보장)
  private MemberDto() throws IllegalStateException {
    throw new IllegalStateException();
  }

  /******************** Request ********************/
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class CreateMemberRequest {
    @NotEmpty
    @Size(max = 10)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String email;

    @NotEmpty
    @Size(max = 100)
    private String password;
    private List<String> roles;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class UpdateMemberRequest {
    @NotEmpty
    @Size(max = 10)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String email;

    @NotEmpty
    @Size(max = 100)
    private String password;
    private List<String> roles;
  }

  /******************** Response *******************/
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class GetMemberResponse {
    // Form input의 속성과 일치시켜야함
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<String> roles;

    public static GetMemberResponse from(Member member) {
      return GetMemberResponse.builder()
          .id(member.getId())
          .name(member.getName())
          .email(member.getEmail())
          .password(member.getPassword())
          .roles(member.getRoles())
          .build();
    }
  }
}
