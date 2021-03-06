package com.itheima.health.service;

import com.itheima.exception.MyException;
import com.itheima.health.pojo.OrderSetting;


import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings) throws MyException;

    List<Map> getOrderSettingByMonth(String month);
}
