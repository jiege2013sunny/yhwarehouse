package com.itheima.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.SetMealService;
import com.itheima.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

/**
 * 定期进行图片清理
 */
public class HealthJob {
    //  配置log日志
    private static final Logger log = LoggerFactory.getLogger(HealthJob.class);
    //    订阅服务
    @Reference
    private SetMealService setMealService;
//    设置触发条件
    @Scheduled()
    public void cleanImg() {
//    查询到千牛云的图片名
        List<String> qiniuImg = QiNiuUtils.listFile();
//    查询到数据库的文件名
        List<String> dbImg = setMealService.findImg();
//        图片集合进行相减
        qiniuImg.remove(dbImg);
//        使用七牛云工具删除
        QiNiuUtils.removeFiles(qiniuImg.toArray(new String[]{}));


        

    }

}
