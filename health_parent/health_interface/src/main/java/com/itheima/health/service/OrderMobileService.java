package com.itheima.health.service;

import com.itheima.exception.MyException;
import com.itheima.health.pojo.Order;

import java.util.Map;

public interface OrderMobileService {
    Order submit(Map<String, String> submitMessage) throws MyException;

    Map<String, Object> findOrderDetailById(int id);
}
