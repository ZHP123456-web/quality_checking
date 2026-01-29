package com.example.demo.controller;

import com.example.demo.bean.CheckRequest;
import com.example.demo.service.BadDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bad-data")
public class BadDataController {

    @Resource
    private BadDataService badDataService;

    /**
     * 查询某表某列不符合规则的数据
     * POST /bad-data/check
     */
    @PostMapping("/check")
    public List<Map<String, Object>> check(@Validated @RequestBody CheckRequest req) {
        return badDataService.listBadData(req.getTableName(), req.getColumnRules());
    }
}