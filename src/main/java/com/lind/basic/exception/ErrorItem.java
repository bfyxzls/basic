package com.lind.basic.exception;

import lombok.ToString;

@ToString
public class ErrorItem {
  private String code;
  private Object value;
  private String message;

  ErrorItem(final String code, final Object value, final String message) {
    this.code = code;
    this.value = value;
    this.message = message;
  }

  public static ErrorItem.ErrorItemBuilder builder() {
    return new ErrorItem.ErrorItemBuilder();
  }

  public String getCode() {
    return this.code;
  }

  public Object getValue() {
    return this.value;
  }

  public String getMessage() {
    return this.message;
  }

  public static class ErrorItemBuilder {
    private String code;
    private Object value;
    private String message;

    ErrorItemBuilder() {
    }

    public ErrorItem.ErrorItemBuilder code(final String code) {
      this.code = code;
      return this;
    }

    public ErrorItem.ErrorItemBuilder value(final Object value) {
      this.value = value;
      return this;
    }

    public ErrorItem.ErrorItemBuilder message(final String message) {
      this.message = message;
      return this;
    }

    public ErrorItem build() {
      return new ErrorItem(this.code, this.value, this.message);
    }

  }
}