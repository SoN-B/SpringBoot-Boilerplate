package SpringGroup.SpringResult.domain.board.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import SpringGroup.SpringResult.domain.member.model.Member;
import lombok.*;

@Builder
@Data
@Entity
@Table(name = "board_table")
@NoArgsConstructor
@AllArgsConstructor
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Column(length = 50)
  private String title;

  @NotEmpty
  @Column(length = 255)
  private String content;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member author;
}
