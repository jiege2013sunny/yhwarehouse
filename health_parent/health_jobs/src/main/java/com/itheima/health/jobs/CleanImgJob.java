package com.itheima.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.SetMealService;
import com.itheima.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定期进行图片清理
 */
@Component
public class CleanImgJob {
    //  配置log日志
    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);
    //    订阅服务
    @Reference
    private SetMealService setMealService;

    //    设置触发条件
    @Scheduled(initialDelay = 1000, fixedDelay = 1800000)
    public void cleanImg() {
//        七牛云图片集合
        List<String> strings = QiNiuUtils.listFile();
//        数据库图片集合
        List<String> dbImg = setMealService.findImg();
//        相减获得垃圾图片集合
        strings.removeAll(dbImg);
//        使用七牛云工具删除(里面放的是未定义的string数组)
        QiNiuUtils.removeFiles(strings.toArray(new String[]{}));
    }

}
