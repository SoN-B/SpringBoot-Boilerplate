package SpringGroup.SpringResult.domain.member.model;

public class Member {
  private Long id;
  private String name;

  // id의 getter, setter
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // name의 getter, setter
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
