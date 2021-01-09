package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

public interface SetMealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);
}
