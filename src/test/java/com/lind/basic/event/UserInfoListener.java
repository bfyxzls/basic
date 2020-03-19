package com.lind.basic.event;

import com.lind.basic.mybatis.UserInfo;
import com.lind.basic.mybatis.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class UserInfoListener {
    @Autowired
    UserMapper userMapper;

    @EventListener
    public void handleOrderEvent(OrderEvent event) {
        UserInfo user = new UserInfo();
        user.setCreateOn(Instant.now());
        user.setUpdatedOn(Instant.now());
        user.setDelete(0);
        user.setEmail("test@sina.com");
      //  user.setName("lind");
        userMapper.insert(user);
    }
}
