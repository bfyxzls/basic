package com.lind.basic.module.message.enums;


import org.apache.commons.lang.StringUtils;

/**
 * 发送消息类型枚举
 */
public enum SendMsgTypeEnum {

    //推送方式：1短信 2邮件 3微信
    SMS("1", "com.lind.basic.module.message.impl.SmsSendMessage"),
    EMAIL("2", "com.lind.basic.module.message.impl.EmailSendMessage"),
    WX("3", "com.lind.basic.module.message.impl.WxSendMessage");

    private String type;

    private String implClass;

    private SendMsgTypeEnum(String type, String implClass) {
        this.type = type;
        this.implClass = implClass;
    }

    public static SendMsgTypeEnum getByType(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        for (SendMsgTypeEnum val : values()) {
            if (val.getType().equals(type)) {
                return val;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImplClass() {
        return implClass;
    }

    public void setImplClass(String implClass) {
        this.implClass = implClass;
    }
}
