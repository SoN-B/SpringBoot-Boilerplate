package SpringGroup.SpringResult.domain.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import SpringGroup.SpringResult.domain.board.model.Board;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/board")
public class BoardController {
  private StringRedisTemplate redisTemplate; // Redis 연산을 수행하는 데 사용
  private ObjectMapper mapper; // JSON 문자열과 Java 객체 간의 변환

  public BoardController(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.mapper = new ObjectMapper();
  }

  @PostMapping
  public void createPost(@RequestBody Board post) throws Exception {
    String postJson = mapper.writeValueAsString(post);
    ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
    ops.set(String.valueOf(post.getId()), postJson, 60, TimeUnit.SECONDS);
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