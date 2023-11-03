package com.epam.testProject.exception;

import com.epam.testProject.exception.errorcode.ErrorCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

  public static final String DATE_TIME_FORMAT = "dd.MM.yy HH:mm:ss";
  private final ErrorCode errorCode;
  private final String dateAndTime;

  public ServiceException(ErrorCode errorCode) {
    this(null, errorCode);
  }

  private ServiceException(Throwable cause, ErrorCode errorCode) {
    super(cause);
    this.errorCode = errorCode;
    dateAndTime = getExceptionDateAndTime();
  }

  @Override
  public String getMessage() {
    return errorCode.getMessage();
  }

  private String getExceptionDateAndTime() {
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
  }
}
