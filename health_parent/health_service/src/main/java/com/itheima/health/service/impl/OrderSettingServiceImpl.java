package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.exception.MyException;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
            if (os != null) {
                if (os.getNumber() >= orderSetting.getReservations()) {
//        数据合法的话,就修改预约信息
                    orderSettingDao.update(orderSetting);
                } else {
//        如果修改后的预约人数大于最大预约人数,就抛异常了
                    throw new MyException("预约人数不能大于最大预约人数嗷");
                }
            }
//        如果没有,直接添加
            else {
                orderSettingDao.add(orderSetting);
            }
        }

    }

    /**
     * 通过当前月份查找预约信息
     *
     * @param month
     * @return
     */
    //根据日期查询预约设置数据
    public List<Map> getOrderSettingByMonth(String month) {//2019-03
        // 1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = month + "-1";//2019-03-1
        String dateEnd = month + "-31";//2019-03-31
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        // 2.查询当前月份的预约设置
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        // 3.将List<OrderSetting>，组织成List<Map>
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }
}
