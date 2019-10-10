package com.lind.basic.event;

import lombok.Builder;
import lombok.Getter;

public class OrderEvent {
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public OrderEvent(String msg) {
        this.msg = msg;
    }
}