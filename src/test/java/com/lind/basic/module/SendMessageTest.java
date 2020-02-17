package com.lind.basic.module;

import com.lind.basic.module.message.SendMessage;
import com.lind.basic.module.message.enums.SendMsgTypeEnum;
import org.junit.Test;

public class SendMessageTest {
    @Test
    public void dynamicMessage() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        SendMessage sendMessage = (SendMessage) Class.forName(SendMsgTypeEnum.EMAIL.getImplClass()).newInstance();
        sendMessage.send("email消息");
    }
}
