package com.itheima.health.service;

import com.itheima.exception.MyException;
import com.itheima.health.pojo.OrderSetting;


import java.util.List;
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings) throws MyException;
}
