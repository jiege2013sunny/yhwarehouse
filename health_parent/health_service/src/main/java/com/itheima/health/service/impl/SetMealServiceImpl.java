package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.exception.MyException;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {
    //    自动注入dao
    @Autowired
    private SetMealDao setMealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
//        先把套餐添加进去
        setMealDao.add(setmeal);
//        判定数组非空,然后添加关系链接
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
//                将套餐id和检查组id一起添加到关联表
                setMealDao.setGroupToSetmeal(setmeal.getId(), checkgroupId);
            }
        }
    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
//        分页小助手设置分页信息
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
//        判断前端传来的参数有没有搜索信息
//        如果不为空
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
//            拼接模糊查询的语句
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
//            进行模糊查询
        }
        Page<Setmeal> setmealPage = setMealDao.findPage(queryPageBean.getQueryString());
        return new PageResult<>(setmealPage.getTotal(), setmealPage.getResult());
    }

    @Override
    public List<Setmeal> findAllSetmeal() {
        return setMealDao.findAllSetmeal();
    }

    @Override
    public Setmeal findDetailById(Integer id) {
        return setMealDao.findDetailById(id);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 修改套餐
        setMealDao.update(setmeal);
        // 删除旧关系(将连接表原来的数据删除)
        setMealDao.deleteSetmealCheckgroup(setmeal.getId());
        // 遍历添加新关系(在连接表中添加新数据)
        for (Integer checkgroupId : checkgroupIds) {
            setMealDao.setGroupToSetmeal(setmeal.getId(), checkgroupId);
        }

    }

    @Override
//    接口和实现类上都要抛异常
    public void deleteById(int id) throws MyException {
//        查找是否和会员有关联,如果有关联就提示无法删除
        int count = setMealDao.findHuiyuan(id);
        if (count <= 0) {
//        通过id删除和检查组的旧关系
            setMealDao.deleteSetmealCheckgroup(id);
//        删除检查套餐的信息
            setMealDao.deleteSetmeal(id);
        } else {
            throw new MyException("与会员有关联,无法删除");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<String> findImg() {
        return setMealDao.findImg();
    }
}
