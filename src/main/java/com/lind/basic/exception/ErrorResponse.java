package com.lind.basic.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.ToString;

@ToString
public class ErrorResponse {
  private int status;
  private String method;
  private String path;
  private Object extra;
  private List<ErrorItem> errors;

  /**
   * init.
   */
  public ErrorResponse() {
  }

  /**
   * init.
   */
  public ErrorResponse addError(String code, String message) {
    ErrorItem errorItem = ErrorItem.builder().code(code).message(message).build();
    this.addError(errorItem);
    return this;
  }

  /**
   * init.
   */
  public ErrorResponse addError(String code, Object value) {
    ErrorItem errorItem = ErrorItem.builder().code(code).value(value).build();
    this.addError(errorItem);
    return this;
  }

  /**
   * init.
   */
  public ErrorResponse addError(ErrorItem errorItem) {
    this.getErrors().add(errorItem);
    return this;
  }

  /**
   * get errors.
   *
   * @return
   */
  public List<ErrorItem> getErrors() {
    if (this.errors == null) {
      this.errors = new ArrayList();
    }

    return this.errors;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }

  public String getMethod() {
    return this.method;
  }

  public void setMethod(final String method) {
    this.method = method;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public Object getExtra() {
    return this.extra;
  }

  public void setExtra(final Object extra) {
    this.extra = extra;
  }

}
