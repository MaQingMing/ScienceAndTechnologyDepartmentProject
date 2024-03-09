package com.yc.common.handler;

import com.yc.common.annotate.CreateTime;
import com.yc.common.annotate.UpdateTime;
import com.yc.entity.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @PACKAGE_NAME: com.yc.common.handler
 * @NAME: MybatisAutoFillingInterceptor
 * @USER: Administrator
 * @DATE: 2023/11/20
 * @MONTH_NAME_SHORT: 11月
 * @MONTH_NAME_FULL: 十一月
 **/
@Slf4j
//@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisAutoFillingInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取 SQL 命令
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        //只判断新增和修改
        if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            // 获取参数
            Object parameter = invocation.getArgs()[1];
            //批量操作时
            Map<String,Object> objectMap = (Map<String, Object>) parameter;
            if (parameter instanceof MapperMethod.ParamMap && objectMap.containsKey("list")) {
                MapperMethod.ParamMap map = (MapperMethod.ParamMap) parameter;
                Object obj = map.get("list");
                List<?> list = (List<?>) obj;
                if (list != null) {
                    for (Object o : list) {
                        setParameter(o, sqlCommandType);
                    }
                }
            } else {
                setParameter(parameter, sqlCommandType);
            }
        }
        return invocation.proceed();
    }

    public void setParameter(Object parameter, SqlCommandType sqlCommandType) throws Throwable {
        Class<?> aClass = parameter.getClass();
        Field[] declaredFields;
        //如果常用字段提取了公共类 BaseEntity
        //判断BaseEntity是否是父类
        if (BaseEntity.class.isAssignableFrom(aClass)) {
            // 获取父类私有成员变量
            declaredFields = aClass.getSuperclass().getDeclaredFields();
        } else {
            // 获取私有成员变量
            declaredFields = aClass.getDeclaredFields();
        }
        for (Field field : declaredFields) {
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                // insert 语句插入 createBy
                /*if (field.getAnnotation(CreateBy.class) != null) {
                    field.setAccessible(true);
                    // 这里实际开发中获取登录用户名，填写真实值
                    field.set(parameter, "fan");
                }*/

                if (field.getAnnotation(CreateTime.class) != null) {
                    // insert 语句插入 createTime
                    field.setAccessible(true);
                    field.set(parameter, LocalDateTime.now());
                }
            }

            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                if (field.getAnnotation(UpdateTime.class) != null) {
                    // update 语句插入 updateTime
                    field.setAccessible(true);
                    field.set("update_time", LocalDateTime.now());
                }
                /*if (field.getAnnotation(UpdateBy.class) != null) {
                    // update 语句插入 updateBy
                    field.setAccessible(true);
                    // 这里实际开发中获取登录用户名，填写真实值
                    field.set(parameter, "fan");
                }*/
            }
        }
    }
}
