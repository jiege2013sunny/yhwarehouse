package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.itheima.exception.MyException;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
//        调用dao层到数据库中查询数据
        List<CheckItem> list = checkItemDao.findAll();
        return list;

    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
//        使用分页小助手设置查询页码和每页条数
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
//        判定后设置查询条件的string类型
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 紧接着的查询语句会被分页
        Page<CheckItem> page = checkItemDao.findByMessage(queryPageBean.getQueryString());
        // 封装到分页结果对象中
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public CheckItem findById(int id) {
        CheckItem checkItem =checkItemDao.findById(id);
        return  checkItem;
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public void deleteById(int id) throws MyException {
//        需要在业务层先进行判定,查询出中间表中是否有检查项的id,
//        如果有,就不删除,抛出一个异常,如果没有,就通过id删除
        int num=checkItemDao.findCount(id);
        if (num>0) {
            throw new MyException(MessageConstant.CHECKITEM_IN_USE);
        }else {
        checkItemDao.deleteById(id);

        }
    }
}
