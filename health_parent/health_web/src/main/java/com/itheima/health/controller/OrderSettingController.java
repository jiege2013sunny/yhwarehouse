package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @PostMapping("/upload")
    public Result upLoadSuccess(@RequestBody MultipartFile excelFile) throws IOException {
        List<String[]> excel = POIUtils.readExcel(excelFile);
        return  null;
    }
}
