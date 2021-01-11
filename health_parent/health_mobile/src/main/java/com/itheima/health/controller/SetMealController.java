package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import com.itheima.utils.QiNiuUtils;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.omg.CORBA.INTERNAL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    /**
     * 获取套餐列表
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result findSerMealEdition() {
        List<Setmeal> setmealList = setMealService.findAllSetmeal();
        setmealList.forEach(setmeal->{
            setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        });
//        for (Setmeal setmeal : setmealList) {
//            String img = setmeal.getImg();
//            String imgurl =QiNiuUtils.DOMAIN+img;
//            setmeal.setImg(imgurl);
//        }
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
    }

    /**
     * 获取套餐列表中的详情
     */
    @GetMapping("/findDetailById")
    public Result findSetmealMessage(Integer id){
        Setmeal setmeal=setMealService.findDetailById(id);
//        拼接图片域名
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmeal);
    }

}
