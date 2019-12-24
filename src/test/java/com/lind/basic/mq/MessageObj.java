package com.lind.basic.mq;

import java.util.Date;

public class MessageObj {
  private String body;
  private Date createBy;

  public MessageObj(String body, Date createBy) {
    this.body = body;
    this.createBy = createBy;
  }

  public MessageObj() {
  }

  public Date getCreateBy() {
    return createBy;
  }

  public void setCreateBy(Date createBy) {
    this.createBy = createBy;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "MessageObj{" + "body='" + body + '\'' + '}';
  }
}
