package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/checkitem"))
public class CheckItemController {
    //    获取订阅服务
    @Reference
    private CheckItemService checkItemService;

//    删除方法
    @RequestMapping("/deleteById")
    public Result deleteById(int id){
        checkItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //    修改方法
//      1,通过id查找检查项来完成数据回显
//      2,通过update请求来更新表单提交过来的更新数据
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem =checkItemService.findById(id);
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        return result;
    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){

        checkItemService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }

    //    对检查项进行分页查询,主要是训练对分页小助手的使用
    @PostMapping("/findPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
//       调用服务查询
        PageResult<CheckItem> pageList = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageList);
    }

    //新增检查项
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        // 调用服务添加
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }


    //    查询所有检查项
    @GetMapping("/findAll")
//    通过result类型,将结果以json形式返回到前端
    public Result CheckItemController() {

//        通过订阅服务service层的方法,将前端传来的数据存到数据库中
        List<CheckItem> list = checkItemService.findAll();

//        将查询到的数据放到result
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);

    }
}
