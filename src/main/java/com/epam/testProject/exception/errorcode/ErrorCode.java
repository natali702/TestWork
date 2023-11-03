
package com.epam.testProject.exception.errorcode;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

public interface ErrorCode extends Serializable {

  HttpStatus defaultHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  String getMessage();

  default HttpStatus getHttpStatus() {
    return defaultHttpStatus;
  }
}
