package com.lind.basic.mybatis;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DbTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserExtensionMapper userExtensionMapper;

    @Test()
    public void insert() {
        UserInfo user = new UserInfo();
        user.setCreateOn(Instant.now());
        user.setUpdatedOn(Instant.now());
        user.setDelete(0);
        user.setEmail("test@sina.com");
        user.setName("lind");
        user.setId(1L);
        userMapper.insert(user);
        EntityWrapper<UserInfo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("name", "lind");
        logger.debug("transactional user count:{}", userMapper.selectCount(entityWrapper).toString());
    }

    @Test()
    @Transactional
    public void transactional() {
        UserInfo user = new UserInfo();
        user.setCreateOn(Instant.now());
        user.setUpdatedOn(Instant.now());
        user.setDelete(0);
        user.setEmail("test@sina.com");
        user.setName("lind");
        userMapper.insert(user);
        EntityWrapper<UserInfo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("name", "lind");
        logger.debug("transactional user count:{}", userMapper.selectCount(entityWrapper).toString());
        try {
            UserExtension userExtension = new UserExtension();
            userExtension.setUserId(1L);
            userExtensionMapper.insert(userExtension);//throw error
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            logger.debug("transactional user count:{}", userMapper.selectCount(entityWrapper).toString());
        }

    }
}
