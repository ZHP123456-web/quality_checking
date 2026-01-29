package com.example.demo.service.impl;

import com.example.demo.bean.CheckRequest;
import com.example.demo.mapper.BadDataMapper;
import com.example.demo.service.BadDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BadDataServiceImpl implements BadDataService {

    @Resource
    private BadDataMapper badDataMapper;

    @Resource
    private DataSource dataSource;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listBadData(String tableName, List<CheckRequest.ColumnRule> columnRules) {
        // 1. 白名单校验表名、列名
        for (CheckRequest.ColumnRule columnRule : columnRules) {
            if (!isLegalTableColumn(tableName, columnRule.getColumn())) {
                throw new IllegalArgumentException("表名或列名不合法");
            }
        }

        // 2. 解析规则JSON，提取正则表达式
        List<CheckRequest.ColumnRule> parsedColumnRules = columnRules.stream()
                .map(cr -> {
                    String pattern = parseRuleJson(cr.getRule());
                    CheckRequest.ColumnRule newCr = new CheckRequest.ColumnRule();
                    newCr.setColumn(cr.getColumn());
                    newCr.setRule(pattern);
                    return newCr;
                })
                .collect(Collectors.toList());

        // 3. 查询违规数据
        return badDataMapper.findBadDataByMultipleRegex(tableName, parsedColumnRules);
    }

    @Override
    public boolean isLegalTableColumn(String table, String col) {
        try (Connection c = dataSource.getConnection()) {
            DatabaseMetaData meta = c.getMetaData();

            // 1. 忽略大小写找表
            String realTable = null;
            try (ResultSet tabs = meta.getTables(c.getCatalog(), null, "%", new String[]{"TABLE"})) {
                while (tabs.next()) {
                    String t = tabs.getString("TABLE_NAME");
                    if (t.equalsIgnoreCase(table)) {
                        realTable = t;
                        break;
                    }
                }
            }
            if (realTable == null) return false;

            // 2. 忽略大小写找列
            try (ResultSet cols = meta.getColumns(c.getCatalog(), null, realTable, "%")) {
                while (cols.next()) {
                    if (cols.getString("COLUMN_NAME").equalsIgnoreCase(col)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public String getPrimaryKey(String table) {
        try (Connection c = dataSource.getConnection()) {
            DatabaseMetaData meta = c.getMetaData();
            try (ResultSet rs = meta.getPrimaryKeys(c.getCatalog(), null, table)) {
                if (rs.next()) return rs.getString("COLUMN_NAME");
                throw new IllegalStateException("表 " + table + " 无主键");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取主键失败", e);
        }
    }

    /**
     * 解析规则JSON，提取正则表达式
     * @param ruleJson 规则JSON字符串
     * @return 正则表达式
     */
    private String parseRuleJson(String ruleJson) {
        try {
            JsonNode node = mapper.readTree(ruleJson);
            if (!"regex".equals(node.get("type").asText())) {
                throw new IllegalArgumentException("暂仅支持 regex 规则");
            }
            String pattern = node.get("pattern").asText();
            if (pattern == null || pattern.trim().isEmpty()) {
                throw new IllegalArgumentException("正则表达式不能为空");
            }
            return pattern;
        } catch (Exception e) {
            throw new IllegalArgumentException("rule 格式非法: " + e.getMessage());
        }
    }
}