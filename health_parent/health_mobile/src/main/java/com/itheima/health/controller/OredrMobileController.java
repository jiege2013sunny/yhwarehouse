package com.itheima.health.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderMobileService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author: 永和战神
 * @Date: 2021/1/12 17:25
 */
@RestController
@RequestMapping("/order")
public class OredrMobileController {
    /**
     * 检验预约信息
     */

//    注入redispool对象
    @Autowired
    private JedisPool jedisPool;

    private final Logger log = LoggerFactory.getLogger(OredrMobileController.class);

    //    注入service服务
    @Reference
    private OrderMobileService orderMobileService;

    @GetMapping("/findById")
    public Result findById(int id){
        // 调用服务通过id查询订单信息
        Map<String,Object> orderInfo = orderMobileService.findOrderDetailById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }

    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> submitMessage) {

//        进行验证码校验,从前端数据中拼接验证码的key
        String telephone = submitMessage.get("telephone");

//        拼接redis中的key
        Jedis jedis = jedisPool.getResource();
        String sendtypeOrder = RedisMessageConstant.SENDTYPE_ORDER;
        String key = sendtypeOrder + "_" + telephone;

//        到redis中通过key查询验证码
        String validate = jedis.get(key);
//        如果为空回复提示信息,请重新发送验证码
        if (StringUtils.isEmpty(validate)) {
            log.debug(validate);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

//        如果不为空,将前端传来的验证码和redis中的进行对比
        String validateCode = submitMessage.get("validateCode");
        if (!validate.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

//        删除redis中的验证码,测试过程中先注释
//        jedis.del(key);

//        给前端传来的数据中添加一个预约类型(微信预约)
        submitMessage.put("orderType",Order.ORDERTYPE_WEIXIN);
        Order order = orderMobileService.submit(submitMessage);

//        走到这里肯定是成功的,所以将前端传来的预约信息传到后端,然后返回前端需要的数据
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

}
