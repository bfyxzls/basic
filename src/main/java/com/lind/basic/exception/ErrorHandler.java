package com.lind.basic.exception;

import com.lind.basic.util.ResponseUtils;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 对controller抛出的异常统一拦截处理.
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(Exception.class)
  private ResponseEntity<ErrorResponse> exception(Exception exception, HttpServletRequest request) {

    logger.error("internal server error", exception);

    ErrorResponse errorResponse = ResponseUtils
        .buildErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, null);
    errorResponse.setExtra(exception.getStackTrace());
    errorResponse.addError("INTERNAL_SERVER_ERROR", "服务端异常");

    logger.error("handle Exception, response = {}", errorResponse);
    return buildResponseEntity(errorResponse);
  }

  @ExceptionHandler(Exceptions.HttpStatusException.class)
  private ResponseEntity<ErrorResponse> httpStatusException(
      Exceptions.HttpStatusException exception,
      HttpServletRequest request) {

    logger.debug("HttpStatusException", exception);

    ErrorResponse errorResponse = ResponseUtils
        .buildErrorResponse(request, exception.getHttpStatus(), null);

    errorResponse.addError(ErrorItem.builder()
        .code(exception.getCode())
        .value(exception.getValue())
        .message(exception.getMessage())
        .build()
    );

    logger.debug("handle HttpStatusException, response = {}", errorResponse);
    return buildResponseEntity(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  private ResponseEntity<ErrorResponse> methodArgumentNotValidException(
      MethodArgumentNotValidException exception, HttpServletRequest request) {

    ErrorResponse errorResponse = ResponseUtils
        .buildErrorResponse(request, HttpStatus.BAD_REQUEST, null);

    BindingResult bindingResult = exception.getBindingResult();

    for (ObjectError bindError : bindingResult.getAllErrors()) {
      String name = bindError.getObjectName();
      String message = bindError.getDefaultMessage();
      if (bindError instanceof FieldError) {
        name = ((FieldError) bindError).getField();
      }
      errorResponse.addError(name, message);
    }

    logger.error("handle MethodArgumentNotValidException, response = {}", errorResponse);
    return buildResponseEntity(errorResponse);
  }

  @ExceptionHandler(IllegalStateException.class)
  private ResponseEntity<ErrorResponse> illegalStateException(IllegalStateException exception,
                                                              HttpServletRequest request) {

    logger.error("IllegalStateException", exception);

    ErrorResponse errorResponse = ResponseUtils
        .buildErrorResponse(request, HttpStatus.PRECONDITION_FAILED, null);
    errorResponse.addError("PRECONDITION_FAILED", exception.getMessage());

    logger.error("handle IllegalStateException, response = {}", errorResponse);
    return buildResponseEntity(errorResponse);
  }

  private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse) {
    return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
  }

  private String getExceptionMessage(Exception exception) {
    String message = exception.getMessage();
    if (message != null) {
      return message;
    }
    return exception.toString();
  }
}
