package SpringGroup.SpringResult.domain.board.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import SpringGroup.SpringResult.domain.board.model.Board;
import SpringGroup.SpringResult.domain.member.model.Member;
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

  /******************** Response ********************/
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class GetBoardResponse {
    private Long id;
    private String title;
    private String content;
    private String author;

    public static GetBoardResponse from(Board board) {
      return GetBoardResponse.builder()
          .id(board.getId())
          .title(board.getTitle())
          .content(board.getContent())
          .author(board.getAuthor().getName())
          .build();
    }
  }
}
