package com.lind.basic.module.message.impl;

import com.lind.basic.module.message.SendMessage;

public class EmailSendMessage implements SendMessage {
    @Override
    public void send(String message) {
        System.out.println("电子邮件消息:" + message);
    }
}
