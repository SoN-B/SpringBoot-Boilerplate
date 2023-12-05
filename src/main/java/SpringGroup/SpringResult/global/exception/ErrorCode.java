package SpringGroup.SpringResult.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  /***************************************************************
   * member
   ****************************************************************/
  MEMBER_UNAUTHORIZED_UPDATE(403, 1000, "Only the owner can change the information."),
  MEMBER_UNAUTHORIZED_DELETE(403, 1001, "Only the owner can delete the information."),
  ;

  private final int statusCode;
  private final int code;
  private final String message;
}
