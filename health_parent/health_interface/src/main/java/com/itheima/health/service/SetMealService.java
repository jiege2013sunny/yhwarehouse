package com.itheima.health.service;

import com.itheima.exception.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetMealService{
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAllSetmeal();

    Setmeal findDetailById(Integer id);

    Setmeal findById(Integer id);

    void update(Setmeal setmeal, Integer[] checkgroupIds);

    void deleteById(int id) throws MyException;

    List<String> findImg();
}
