package SpringGroup.SpringResult.domain.board.controller;

import SpringGroup.SpringResult.domain.board.dto.BoardDto.CreateBoardRequest;
import SpringGroup.SpringResult.domain.board.dto.BoardDto.GetBoardResponse;
import SpringGroup.SpringResult.domain.board.service.BoardService;
import SpringGroup.SpringResult.domain.member.model.Member;
import SpringGroup.SpringResult.global.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  private final BoardService boardService;

  /**
   * 게시글 생성
   *
   * @param postInfo 사용자가 입력한 게시글 정보 (제목, 내용)
   *
   * @exception 1. 로그인하지 않은 사용자가 게시글을 작성할 경우
   */
  @PostMapping
  public ResponseEntity<Response> createPost(@AuthenticationPrincipal Member currentMember,
      @RequestBody @Valid CreateBoardRequest postInfo) {
    boardService.createBoard(currentMember, postInfo);
    return ResponseEntity.ok(Response.success("Post created."));
  }

  /**
   * 모든 게시글 가져오기
   *
   * @exception 1. 게시글이 없을 경우
   */
  @GetMapping
  public ResponseEntity<Response> getAllPosts() {
    List<GetBoardResponse> posts = boardService.findAll();
    return ResponseEntity.ok(Response.success(posts));
  }

  // @GetMapping("/{id}")
  // public Board getPost(@PathVariable String id) throws Exception {
  // ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
  // String postJson = ops.get(id);
  // return mapper.readValue(postJson, Board.class);
  // }

  // @PutMapping("/{id}")
  // public void updatePost(@PathVariable String id, @RequestBody Board post)
  // throws Exception {
  // String postJson = mapper.writeValueAsString(post);
  // ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
  // ops.set(id, postJson, 60, TimeUnit.SECONDS);
  // }

  // @DeleteMapping("/{id}")
  // public void deletePost(@PathVariable String id) {
  // redisTemplate.delete(id);
  // }
}