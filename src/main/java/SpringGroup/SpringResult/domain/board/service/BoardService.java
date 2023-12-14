package SpringGroup.SpringResult.domain.board.service;

import java.util.List;

import SpringGroup.SpringResult.domain.board.dto.BoardDto.CreateBoardRequest;
import SpringGroup.SpringResult.domain.board.dto.BoardDto.GetBoardResponse;
import SpringGroup.SpringResult.domain.member.model.Member;

public interface BoardService {
  void createBoard(Member currentMember, CreateBoardRequest postInfo);

  List<GetBoardResponse> findAll();
}
