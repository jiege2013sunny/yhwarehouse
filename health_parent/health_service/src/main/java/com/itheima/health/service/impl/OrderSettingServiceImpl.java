package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    /**
     * 通过pojo中的时间到数据库中进行查询,然后做对应的操作
     *
     * @param orderSettings
     */
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettings) {
//        遍历集合,然后进行判断
        for (OrderSetting orderSetting : orderSettings) {
//            判断是否有这个信息,如果有,就修改
            if (orderSetting!=null) {
                orderSettingDao.update(orderSetting);
            }

        }
    }
}
