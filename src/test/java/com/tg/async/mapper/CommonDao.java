package com.tg.async.mapper;

import com.tg.async.annotation.*;
import com.tg.async.base.DataHandler;
import com.tg.async.constant.Criterions;

import java.util.List;
import java.util.Map;

/**
 * Created by twogoods on 2018/3/23.
 */
@Sql(User.class)
public interface CommonDao {

    /**
     * 1. api 改异步模式，对象映射
     * 2. 注解形式表达动态sql
     * 3. 无法简单表达的sql可以手写
     * 4. 事务实现，编程式和声明式实现
     * <p>
     * 参考：dbutils mybatis ognl
     */
    @Select
    @Page
    @ModelConditions({
            @ModelCondition(field = "username", criterion = Criterions.EQUAL),
            @ModelCondition(field = "minAge", column = "age", criterion = Criterions.GREATER),
            @ModelCondition(field = "maxAge", column = "age", criterion = Criterions.LESS)
    })
    void query(User user, DataHandler<List<User>> handler);

    void querySingle(User user, DataHandler<User> handler);

    void querySingleMap(User user, DataHandler<Map> handler);

    void insert(User user,DataHandler<Long> handler);

    void update(User user,DataHandler<Long> handler);

    void delete(User user,DataHandler<Long> handler);
}
