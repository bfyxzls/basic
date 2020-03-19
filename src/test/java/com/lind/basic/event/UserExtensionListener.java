package com.lind.basic.event;

import com.lind.basic.mybatis.UserExtension;
import com.lind.basic.mybatis.UserExtensionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserExtensionListener {
    @Autowired
    UserExtensionMapper userExtensionMapper;

    @EventListener
    public void handleOrderEvent(OrderEvent event) {
        UserExtension userExtension = new UserExtension();
        userExtension.setUserId(1L);
        userExtension.setRealName("zzl");
        userExtensionMapper.insert(userExtension);//throw error
    }
}
