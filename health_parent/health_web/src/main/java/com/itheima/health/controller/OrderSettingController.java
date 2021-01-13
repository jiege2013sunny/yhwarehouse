package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    /**
     * em...Excel文件上传项目
     *
     * @param excelFile
     * @return
     * @throws IOException
     */
//    订阅服务
    @Reference
    private OrderSettingService orderSettingService;

    /**
     *动态展示当前月份预约信息
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result findOrderOettingByMonth(String month){
//        通过月份查询预约信息
        List<Map> osList=orderSettingService.getOrderSettingByMonth(month);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,osList);
    }

    /**
     *通过表格批量上传数据信息
     * @param excelFile
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upLoadSuccess(@RequestBody MultipartFile excelFile) throws IOException {
//        使用工具类获得Excel文件的list集合对象
        List<String[]> excel = POIUtils.readExcel(excelFile);
//        创建格式日期对象,为了转化数据形式然后方便到数据库查询
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);
//        使用stream流进行数据转化,将string[]转化成pojo里面的ordersetting
        List<OrderSetting> orderSettings = excel.stream().map(arr -> {
            OrderSetting orderSetting = new OrderSetting();
            try {
                Date orderDate = simpleDateFormat.parse(arr[0]);
                int number = Integer.parseInt(arr[1]);
                orderSetting.setOrderDate(orderDate);
                orderSetting.setNumber(number);
            } catch (ParseException e) {
            }
            return orderSetting;
        }).collect(Collectors.toList());
//        使用业务层方法 来进行文件上传
        orderSettingService.add(orderSettings);

//        返回结果集
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);


    }
}
