package com.cyiplus.scarlett.config;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class); 
        this.strictInsertFill(metaObject, "editTime", () -> LocalDateTime.now(), LocalDateTime.class); 
        this.strictInsertFill(metaObject, "version", () -> LocalDateTime.now(), LocalDateTime.class); 
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "editTime", () -> LocalDateTime.now(), LocalDateTime.class); 
        this.strictUpdateFill(metaObject, "version", () -> LocalDateTime.now(), LocalDateTime.class); 
    }
}