package SpringGroup.SpringResult.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL) // JSON으로 변환할 때 null 값인 속성을 제외하도록 지시
public class Response<T> {
  private boolean success;
  private int code;
  private String message;
  private T data; // 동적으로 타입을 결정

  @Builder
  public Response(boolean success, int code, String message, T data) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static Response success() {
    return Response.builder()
        .success(true)
        .code(200)
        .message("success")
        .data(null)
        .build();
  }

  public static Response success(String message) {
    return Response.builder()
        .success(true)
        .code(200)
        .message(message)
        .data(null)
        .build();
  }

  public static <T> Response success(T data) {
    return Response.builder()
        .success(true)
        .code(200)
        .message("success")
        .data(data)
        .build();
  }

  public static <T> Response success(T data, String message) {
    return Response.builder()
        .success(true)
        .code(200)
        .message(message)
        .data(data)
        .build();
  }
}
