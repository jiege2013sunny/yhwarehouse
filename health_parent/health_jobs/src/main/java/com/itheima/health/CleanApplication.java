package com.itheima.health;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: 永和战神
 * @Date: 2021/1/12 0:52
 */
public class CleanApplication {
    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("classpath:spring-jobs.xml");
        System.in.read();

    }

}
