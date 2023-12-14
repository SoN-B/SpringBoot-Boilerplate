package SpringGroup.SpringResult.domain.board.exception;

import SpringGroup.SpringResult.global.exception.CustomException;
import SpringGroup.SpringResult.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardException extends CustomException {
  public BoardException(ErrorCode errorCode) {
    super(errorCode);
  }
}
