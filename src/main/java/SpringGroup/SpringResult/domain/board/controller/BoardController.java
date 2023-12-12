package SpringGroup.SpringResult.domain.board.controller;

// import com.fasterxml.jackson.databind.ObjectMapper;

import SpringGroup.SpringResult.domain.board.model.Board;
import SpringGroup.SpringResult.domain.board.repository.BoardRepository;
import SpringGroup.SpringResult.global.response.Response;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  private final StringRedisTemplate redisTemplate; // Redis 연산을 수행하는 데 사용
  private final BoardRepository boardRepository; // DB 연산을 수행하는 데 사용
  // private final ObjectMapper mapper; // JSON 문자열과 Java 객체 간의 변환

  /**
   * 게시글 생성
   *
   * @param postInfo 사용자가 입력한 게시글 정보 (제목, 내용)
   */
  @PostMapping
  public ResponseEntity<Response> createPost(@RequestBody Board postInfo) throws Exception {
    // 게시글을 DB에 저장하는 로직
    boardRepository.save(postInfo);

    // 게시글 ID를 Redis에 저장
    ListOperations<String, String> ops = this.redisTemplate.opsForList();
    ops.rightPush("postIds", String.valueOf(postInfo.getId()));

    return ResponseEntity.ok(Response.success("Post created."));
  }

  /**
   * 모든 게시글 가져오기
   */
  @GetMapping
  public ResponseEntity<Response> getAllPosts() throws Exception {
    ListOperations<String, String> ops = this.redisTemplate.opsForList();
    List<String> postIds = ops.range("postIds", 0, -1);

    List<Long> postIdLongs = postIds.stream()
        .map(Long::valueOf)
        .collect(Collectors.toList());

    List<Board> posts = boardRepository.findAllById(postIdLongs);

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