package SpringGroup.SpringResult.domain.board.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import SpringGroup.SpringResult.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "SpringGroup.SpringResult.domain.board.controller")
public class BoardAdvice {
  private final HttpServletRequest request;

  // MemberException이 발생하면, 해당 메서드가 실행된다.
  // (이 과정에서 JsonProcessingException이 발생하면 이를 처리하지 않고 던집니다.)
  @ExceptionHandler(BoardException.class)
  public ResponseEntity<Response> handleBoardException(BoardException exception) throws JsonProcessingException {
    // Java <-> Json 변환을 위한 ObjectWriter
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    Map<String, Object> params = new HashMap<>();
    ResponseEntity<Response> result = ResponseEntity
        .status(exception.getStatus())
        .body(Response.error(exception));

    params.put("request-url", request.getRequestURI());
    params.put("http-method", request.getMethod());
    params.put("authorization", request.getHeader("Authorization"));
    params.put("params", getParams());
    params.put("query-string", request.getQueryString());
    params.put("time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

    log.error("[BoardException] requestLog : {}", objectWriter.writeValueAsString(params));
    log.error("[BoardException] responseLog : {}", objectWriter.writeValueAsString(result));

    return result;
  }

  // request의 parameter를 JSONObject로 변환
  private JSONObject getParams() {
    JSONObject jsonObject = new JSONObject();
    Enumeration<String> params = request.getParameterNames();
    while (params.hasMoreElements()) {
      String param = params.nextElement();
      String replaceParam = param.replace("\\.", "-");
      jsonObject.put(replaceParam, request.getParameter(param));
    }
    return jsonObject;
  }
}
