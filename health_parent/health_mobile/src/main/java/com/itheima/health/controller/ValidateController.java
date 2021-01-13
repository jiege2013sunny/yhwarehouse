package com.itheima.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: 永和战神
 * @Date: 2021/1/12 16:28
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateController {
    //    注入jedis
    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送验证码
     * 使用redis存储
     */
    @PostMapping("/send4Order")
    public Result checkValidate(String telephone) throws Exception {
//        到redis中查找是否有验证码信息
        Jedis jedis = jedisPool.getResource();
//        设置手机号在redis中的key
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        String codeInRedis = jedis.get(key);
//        如果redis中没有值,发送验证码,生成随机验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        if (codeInRedis == null) {
            try {
//            发送短信验证
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode + "");
//            将验证码添加到jedis,设置存活时间
                jedis.setex(key, 60 * 10, validateCode + "");
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE);
    }
}
