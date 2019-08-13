package com.lind.basic.util;

import com.google.common.collect.Maps;
import com.lind.basic.exception.ErrorItem;
import com.lind.basic.exception.ErrorResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public class ResponseUtils {

  /**
   * Suppress default constructor for noninstantiability.
   */
  private ResponseUtils() {
    throw new AssertionError();
  }
  
  public static <T> ResponseEntity<?> okMessage(String message) {
    return ResponseEntity.ok(buildResponseBody(200, message, null));
  }

  public static ResponseEntity<?> ok(Object body) {
    return ResponseEntity.ok(buildResponseBody(200, "操作成功", body));
  }

  /**
   * 构造返回参数工具.
   */
  public static ResponseEntity<?> ok(Object body, String message) {
    return ResponseEntity.ok(buildResponseBody(200, message, body));
  }

  /**
   * 创建操作成功的响应Mono.
   */
  public static ResponseEntity<ErrorResponse> ok(HttpServletRequest request, String successCode) {
    return buildResponseEntity(buildErrorResponse(request, HttpStatus.OK, successCode, null));
  }

  public static ResponseEntity<?> okNullBody() {
    return ResponseEntity.ok(ResponseBody.builder().status(200).message("操作成功").data(null).build());
  }

  public static ResponseEntity<?> badRequest(Object body, String message) {
    return ResponseEntity.ok(buildResponseBody(400, message, body));
  }

  public static ResponseEntity<?> badRequest(String message) {
    return ResponseEntity.ok(buildResponseBody(400, message, null));
  }

  public static ResponseEntity<?> internalServerError(String message) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(buildResponseBody(500, message, null));
  }

  private static ResponseBody buildResponseBody(int statusCode, String message, Object body) {
    return ResponseBody.builder().status(statusCode).message(message)
        .data(body == null ? Maps.newHashMap() : body).build();
  }

  /**
   * 构建ResponseEntity.
   *
   * @param errorResponse .
   * @return .
   */
  public static ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse) {
    return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
  }

  /**
   * 构建ResponseEntity.
   *
   * @param request    .
   * @param httpStatus .
   * @param errorCode  .
   * @param value      .
   * @return .
   */
  public static ErrorResponse buildErrorResponse(
      HttpServletRequest request,
      HttpStatus httpStatus,
      String errorCode,
      Object value) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(httpStatus.value());
    errorResponse.setMethod(request.getMethod());
    errorResponse.setPath(getFullRequestUrl(request));
    errorResponse.addError(errorCode, value);
    return errorResponse;
  }

  /**
   * 构建ResponseEntity.
   *
   * @param request    .
   * @param httpStatus .
   * @param errorItems .
   * @return .
   */
  public static ErrorResponse buildErrorResponse(
      HttpServletRequest request,
      HttpStatus httpStatus,
      List<ErrorItem> errorItems) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(httpStatus.value());
    errorResponse.setMethod(request.getMethod());
    errorResponse.setPath(getFullRequestUrl(request));
    if (CollectionUtils.isNotEmpty(errorItems)) {
      errorItems
          .stream()
          .forEach(errorItem -> {
            errorResponse.addError(errorItem);
          });
    }
    return errorResponse;
  }

  /**
   * 返回请求URI.
   *
   * @param request .
   * @return .
   */
  public static String getFullRequestUrl(HttpServletRequest request) {
    String fullRequestUrl = request.getRequestURI();
    String queryString = request.getQueryString();
    if (StringUtils.isNotBlank(queryString)) {
      fullRequestUrl = fullRequestUrl + "?" + queryString;
    }
    return fullRequestUrl;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  static class ResponseBody {

    private int status;
    private String message;
    private Object data;
  }

}
