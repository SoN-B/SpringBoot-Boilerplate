package SpringGroup.SpringResult.domain.member.exception;

import SpringGroup.SpringResult.global.exception.CustomException;
import SpringGroup.SpringResult.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends CustomException {
  public MemberException(ErrorCode errorCode) {
    super(errorCode);
  }
}
