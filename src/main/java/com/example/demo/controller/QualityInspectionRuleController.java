package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.bean.QualityInspectionRule;
import com.example.demo.service.IQualityInspectionRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quality-rule")
@RequiredArgsConstructor
@Tag(name = "质检规则接口")
public class QualityInspectionRuleController {

    private final IQualityInspectionRuleService ruleService;

    /* ---------- 增 ---------- */
    @PostMapping("/add")
    @Operation(summary = "新增规则")
    public ResponseEntity<Map<String, Object>> create(@RequestBody QualityInspectionRule dto) {
        ruleService.save(dto);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", dto.getUnid());
        result.put("message", "新增成功");
        return ResponseEntity.ok(result);
    }

    /* ---------- 删 ---------- */
    @DeleteMapping("/delete")
    @Operation(summary = "删除规则")
    public ResponseEntity<Map<String, Object>> delete(@RequestParam String unid) {
        ruleService.removeById(unid);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "删除成功");
        return ResponseEntity.ok(result);
    }

    /* ---------- 改 ---------- */
    @PutMapping("/update")
    @Operation(summary = "更新规则")
    public ResponseEntity<Map<String, Object>> update(@RequestParam String unid,
                                                      @RequestBody QualityInspectionRule dto) {
        dto.setUnid(unid);
        boolean success = ruleService.updateById(dto);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return success ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /* ---------- 单查 ---------- */
    @GetMapping("/findById")
    @Operation(summary = "根据主键查询")
    public ResponseEntity<Map<String, Object>> get(@RequestParam String unid) {
        QualityInspectionRule rule = ruleService.getById(unid);
        Map<String, Object> result = new HashMap<>();
        if (rule != null) {
            result.put("success", true);
            result.put("data", rule);
            result.put("message", "查询成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "未找到对应规则");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    /* ---------- 列表+分页+模糊 ---------- */
    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public ResponseEntity<Map<String, Object>> page(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "5") long size,
            @RequestParam(required = false) String ruleName,
            @RequestParam(required = false) String punid) {
        LambdaQueryWrapper<QualityInspectionRule> qw = new LambdaQueryWrapper<>();
        qw.like(ruleName != null, QualityInspectionRule::getRuleName, ruleName)
                .eq(punid != null, QualityInspectionRule::getPunid, punid)
                .orderByDesc(QualityInspectionRule::getUnid);
        IPage<QualityInspectionRule> pageResult = ruleService.page(new Page<>(current, size), qw);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", pageResult);
        result.put("message", "查询成功");
        return ResponseEntity.ok(result);
    }

    /* ---------- 批量删除 ---------- */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除")
    public ResponseEntity<Map<String, Object>> batchDelete(@RequestBody List<String> unids) {
        boolean success = ruleService.removeByIds(unids);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "批量删除成功" : "批量删除失败");
        return success ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}