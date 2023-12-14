package SpringGroup.SpringResult.domain.board.service;

import SpringGroup.SpringResult.domain.board.dto.BoardDto.CreateBoardRequest;
import SpringGroup.SpringResult.domain.member.model.Member;

public interface BoardService {
  void createBoard(Member currentMember, CreateBoardRequest postInfo);
}
