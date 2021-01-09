package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.exception.MyException;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

//        通过日期,从数据库中查询看是否有信息
            OrderSetting os = orderSettingDao.findByDate(orderSetting.getOrderDate());
//        如果有,在进行预约人数判断
            if (os!=null) {
                if (os.getNumber()>=orderSetting.getReservations()) {
//        数据合法的话,就修改预约信息
                    orderSettingDao.update(orderSetting);
                }else {
//        如果修改后的预约人数大于最大预约人数,就抛异常了
                    throw new MyException("预约人数不能大于最大预约人数嗷");
                }
            }
//        如果没有,直接添加
            else{
                orderSettingDao.add(orderSetting);
            }
        }

    }
}
