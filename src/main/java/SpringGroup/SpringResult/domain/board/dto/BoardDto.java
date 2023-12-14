package SpringGroup.SpringResult.domain.board.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BoardDto {
  private String title;
  private String content;

  private BoardDto() throws IllegalStateException {
    throw new IllegalStateException();
  }

  /******************** Request ********************/
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class CreateBoardRequest {
    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotEmpty
    @Size(max = 255)
    private String content;
  }
}
