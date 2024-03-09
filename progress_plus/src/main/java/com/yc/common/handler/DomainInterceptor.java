package com.yc.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yc.entity.Governuser;
import com.yc.entity.Systemuser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @program: science_merit
 * @description: mybtis-push 字段填充
 * @author: 作者 huchaojie
 * @create: 2023-04-06 16:17
 */
@Component
public class DomainInterceptor implements MetaObjectHandler {

    @Resource
    private HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
        createField(metaObject);
        updateField(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        updateField(metaObject);
    }


    /**
     * @Field 创建人
     * @Field 创建时间
     * */
    public void createField(MetaObject metaObject){
        if(Objects.nonNull(request.getSession().getAttribute("isAdmin"))){
            Boolean isAdmin =(Boolean) request.getSession().getAttribute("isAdmin");
            if (isAdmin){
                Map<String,Object> governuser = (Map<String,Object>) request.getSession().getAttribute("governuser");
                if(Objects.nonNull(governuser)){
                    this.strictInsertFill(metaObject,"createBy", String.class, "" + governuser.get("id"));
                    this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                }
            }else {
                Map<String,Object> systemuser = (Map<String,Object>) request.getSession().getAttribute("systemuser");
                if(Objects.nonNull(systemuser)){
                    this.strictInsertFill(metaObject,"createBy", String.class, "" + systemuser.get("id"));
                    this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                }
            }
        }else{
            this.strictInsertFill(metaObject,"updateBy", String.class, "-1");
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    /**
     * @Field 修改人
     * @Field 修改时间
     * */
    public void updateField(MetaObject metaObject){
        if(Objects.nonNull(request.getSession().getAttribute("isAdmin"))){
            Boolean isAdmin =(Boolean) request.getSession().getAttribute("isAdmin");
            if (isAdmin){
                Map<String,Object> governuser = (Map<String,Object>) request.getSession().getAttribute("governuser");
                if(Objects.nonNull(governuser)){
                    this.strictInsertFill(metaObject,"createBy", String.class, "" + governuser.get("id"));
                    this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                }
            }else {
                Map<String,Object> systemuser = (Map<String,Object>) request.getSession().getAttribute("systemuser");
                if(Objects.nonNull(systemuser)){
                    this.strictInsertFill(metaObject,"createBy", String.class, "" + systemuser.get("id"));
                    this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                }
            }
        }else{
            this.strictInsertFill(metaObject,"updateBy", String.class, "-1");
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }

    }
}
