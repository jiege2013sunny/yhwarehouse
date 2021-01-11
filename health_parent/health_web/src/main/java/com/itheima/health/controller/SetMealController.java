package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import com.itheima.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    //    订阅服务
    @Reference
    private SetMealService setMealService;
    /**
     * 通过id查询套餐信息
     */
    @GetMapping("/findSetmealById")
    public Result findSetmealById(Integer id){
        Setmeal setmeal = setMealService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("domain",QiNiuUtils.DOMAIN);
        map.put("setmeal",setmeal);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,map);

    }

    /**
     * 进行分页查询套餐信息
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> pageResult = setMealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);

    }


    /**
     * 添加检查套餐信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
//        调用业务层方法新增套餐
        setMealService.add(setmeal, checkgroupIds);
//        返回result到前端
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }


    /**
     * 进行图片上传
     *
     * @param multipartFile
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile multipartFile) throws Exception {
//        获取全名
        String originalFilename = multipartFile.getOriginalFilename();
//        截取后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        获取随机名和后缀进行拼接
        UUID uuid = UUID.randomUUID();
        String uploadname = uuid + extension;
//        使用七牛工具类进行图片上传
        QiNiuUtils.uploadViaByte(multipartFile.getBytes(), uploadname);
//        将图片名字以map形式响应到前端
        Map<String, String> map = new HashMap<>();
        map.put("filename", uploadname);
        String domain = QiNiuUtils.DOMAIN;
        map.put("url", domain);

        return new Result(true, MessageConstant.UPLOAD_SUCCESS, map);
    }


    //========================================================================
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        setMealService.update(setmeal, checkgroupIds);
        return new Result(true, "修改套餐成功");
    }

    /**
     * 删除套餐
     */
    @GetMapping("/deleteByID")
    public Result deleteById(int id){
        setMealService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }


}
