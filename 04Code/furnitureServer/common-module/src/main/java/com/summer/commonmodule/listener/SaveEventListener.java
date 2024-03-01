package com.summer.commonmodule.listener;

import com.summer.commonmodule.annotation.AutoIncKey;
import com.summer.commonmodule.entity.model.IncInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * 监听Mongo Event 继承AbstractMongoEventListener类
 * 需要在JAVA对象转换成数据库对象的时候操作id字段实现id自增，所以覆盖onBeforeConvert方法
 * @author WangLang
 */
@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger= LoggerFactory.getLogger(SaveEventListener.class);

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        logger.info(event.getSource().toString());
        ReflectionUtils.doWithFields(event.getSource().getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(java.lang.reflect.Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);
                // 如果字段添加了我们自定义的AutoIncKey注解
                if (field.isAnnotationPresent(AutoIncKey.class)) {
                    // 设置自增ID
                    field.set(event.getSource(), getNextId(event.getSource().getClass().getSimpleName()));
                }
            }
        });
    }

    private Integer getNextId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("incId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        IncInfo inc= mongoTemplate.findAndModify(query, update, options, IncInfo.class);

        return inc.getIncId();
    }
}
