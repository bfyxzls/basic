package com.lind.basic.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * @program: smartLaw
 * @description:
 * @author: jiliying
 * @create: 2019-06-18
 */
//拦截StatementHandler类中参数类型为Statement的 prepare 方法
//即拦截 Statement prepare(Connection var1, Integer var2) 方法
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
@Component
public class MybatisInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(handler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        //获取id，决定是否放行
        String id = mappedStatement.getId(); // 包含方法名。如：com.smartLaw.UserMapper.selectById
        //sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();
        logger.info("namespace============>" + id);
        logger.info("反射之前sql======================>" + sql);
        if ("SELECT".equals(sqlCommandType)) {
            logger.info("select处理");
        }
        if ("INSERT".equals(sqlCommandType)) {
            logger.info("INSERT处理");
        }
        if ("UPDATE".equals(sqlCommandType)) {
            logger.info("UPDATE处理");
        }
        if ("DELETE".equals(sqlCommandType)) {
            logger.info("DELETE处理");
        }
        //通过反射修改sql语句
        //主要和业务代码有关
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, sql);
        logger.info("namespace============>" + id);
        logger.info("反射之后sql======================>" + boundSql.getSql());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //此处可以接收到配置文件的property参数
    }

}
