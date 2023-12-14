package SpringGroup.SpringResult.domain.board.service;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import SpringGroup.SpringResult.domain.board.dto.BoardDto.CreateBoardRequest;
import SpringGroup.SpringResult.domain.board.exception.BoardException;
import SpringGroup.SpringResult.domain.board.model.Board;
import SpringGroup.SpringResult.domain.board.repository.BoardRepository;
import SpringGroup.SpringResult.domain.member.model.Member;
import SpringGroup.SpringResult.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
  private final StringRedisTemplate redisTemplate; // Redis 연산을 수행하는 데 사용
  private final BoardRepository boardRepository; // DB 연산을 수행하는 데 사용
  // private final ObjectMapper mapper; // JSON 문자열과 Java 객체 간의 변환

  @Override
  public void createBoard(Member currentMember, CreateBoardRequest postInfo) {
    // 현재 로그인한 사용자의 정보를 확인
    if (currentMember == null)
      throw new BoardException(ErrorCode.BOARD_UNAUTHORIZED_CREATE);

    Board newPost = Board.builder()
        .title(postInfo.getTitle())
        .content(postInfo.getContent())
        .author(currentMember)
        .build();

    // 게시글을 DB에 저장
    boardRepository.save(newPost);

    // 게시글 ID를 Redis에 저장
    ListOperations<String, String> ops = this.redisTemplate.opsForList();
    ops.rightPush("postIds", String.valueOf(newPost.getId()));
  }
}
