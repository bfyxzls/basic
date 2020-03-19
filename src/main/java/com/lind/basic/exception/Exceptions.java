package com.lind.basic.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * http exception.
 */
public class Exceptions {

  /**
   * 请求参数有误.
   */
  public static RuntimeException badRequestParams(String msg) {
    return HttpStatusException.of(HttpStatus.BAD_REQUEST, null, null, msg);
  }

  /**
   * 未授权.
   **/
  public static RuntimeException unauthorized(String msg) {
    return HttpStatusException.of(HttpStatus.UNAUTHORIZED, null, null, msg);
  }

  /**
   * 状态码异常.
   */
  @Getter
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  static class HttpStatusException extends RuntimeException {

    /**
     * 状态码.
     */
    private final HttpStatus httpStatus;

    /**
     * code.
     */
    private final String code;

    /**
     * code的附加数据.
     */
    private final Object value;

    /**
     * 错误消息.
     */
    private final String message;

    /**
     * 创建RuntimeException.
     *
     * @param httpStatus httpStatus
     * @param code       code
     * @param value      value
     * @param message    message
     * @return {@link RuntimeException}
     */
    static RuntimeException of(final HttpStatus httpStatus, final String code,
                               final Object value, final String message) {
      return new HttpStatusException(httpStatus, code, value, message);
    }

    /**
     * badRequest.
     */
    static RuntimeException badRequest(String code, Object value) {
      return of(HttpStatus.BAD_REQUEST, code, value, null);
    }

    /**
     * badRequest.
     */
    static RuntimeException badRequest(String code, String message) {
      return of(HttpStatus.BAD_REQUEST, code, null, message);
    }

    /**
     * internalServerError.
     */
    static RuntimeException internalServerError(String code, Object value) {
      return of(HttpStatus.INTERNAL_SERVER_ERROR, code, value, null);
    }

    /**
     * internalServerError.
     */
    static RuntimeException internalServerError(String code, String message) {
      return of(HttpStatus.INTERNAL_SERVER_ERROR, code, null, message);
    }

    /**
     * notFound.
     */
    static RuntimeException notFound(String code, Object value) {
      return of(HttpStatus.NOT_FOUND, code, value, null);
    }

    /**
     * notFound.
     */
    static RuntimeException notFound(String code, String message) {
      return of(HttpStatus.NOT_FOUND, code, null, message);
    }
  }

}
