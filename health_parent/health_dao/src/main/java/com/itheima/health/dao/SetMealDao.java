package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealDao {
    //    添加套餐信息
    void add(Setmeal setmeal);

    //    将套餐和检查组关联到一起
    void setGroupToSetmeal(@Param("setmeal_id") Integer setmeal_id, @Param("checkgroup_id") Integer checkgroupId);

    /**
     * 分页小助手查询分页结果
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(@Param("queryString") String queryString);

    List<Setmeal> findAllSetmeal();

    Setmeal findDetailById(Integer id);

    Setmeal findById(Integer id);

    void update(Setmeal setmeal);


    void deleteSetmealCheckgroup(Integer id);

    int findHuiyuan(int id);

    void deleteSetmeal(int id);

    List<String> findImg();
}
