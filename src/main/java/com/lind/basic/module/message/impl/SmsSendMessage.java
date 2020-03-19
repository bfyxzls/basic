package com.lind.basic.module.message.impl;

import com.lind.basic.module.message.SendMessage;

public class SmsSendMessage implements SendMessage {
    @Override
    public void send(String message) {
        System.out.println("短信消息:"+message);
    }
}
