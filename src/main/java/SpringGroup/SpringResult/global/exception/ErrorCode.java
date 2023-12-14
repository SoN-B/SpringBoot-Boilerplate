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
  MEMBER_NOT_FOUND(404, 1002, "Member not found."),
  MEMBER_DUPLICATED_EMAIL(409, 1003, "Email already exists."),
  MEMBER_PASSWORD_NOT_MATCHED(400, 1004, "Password not matched."),
  /***************************************************************
   * board
   ****************************************************************/
  BOARD_UNAUTHORIZED_CREATE(403, 2000, "You must be logged in to create a post."),
  ;

  private final int statusCode;
  private final int code;
  private final String message;
}
