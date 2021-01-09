package com.itheima.health.service;

import com.itheima.exception.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.QueryPageBean;

import java.util.List;

public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    CheckItem findById(int id);

    void update(CheckItem checkItem);

    void deleteById(int id) throws MyException;

}
