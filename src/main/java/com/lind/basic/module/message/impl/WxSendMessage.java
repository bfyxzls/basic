package com.lind.basic.module.message.impl;

import com.lind.basic.module.message.SendMessage;

public class WxSendMessage implements SendMessage {
    @Override
    public void send(String message) {
        System.out.println("微信消息:" + message);
    }
}
