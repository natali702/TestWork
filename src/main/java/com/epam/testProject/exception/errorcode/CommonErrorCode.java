
package com.epam.testProject.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

  FAILED_PARSE_CONTENT("Failed parse the content of the given InputStream as an XML"
      , HttpStatus.BAD_REQUEST),
  BOOK_NOT_EXIST("Book doesn't exist in database", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus httpStatus;
}
