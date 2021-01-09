package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OrderSettingDao {
    OrderSetting findByDate(@Param("orderDate") Date orderDate);

    void update(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);
}
