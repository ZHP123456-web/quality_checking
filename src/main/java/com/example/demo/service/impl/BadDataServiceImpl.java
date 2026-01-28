package com.example.demo.service.impl;

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

@Service
public class BadDataServiceImpl implements BadDataService {

    @Resource
    private BadDataMapper badDataMapper;

    @Resource
    private DataSource dataSource;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listBadData(String tableName, String column, String ruleJson) {
        // 1. 解析规则
        String pattern = parseRuleJson(ruleJson);

        // 2. 白名单校验表名、列名
        if (!isLegalTableColumn(tableName, column)) {
            throw new IllegalArgumentException("表名或列名不合法");
        }

        // 3. 查询违规数据
        return badDataMapper.findBadDataByRegex(tableName, column, pattern);
    }

    @Override
    public boolean isLegalTableColumn(String table, String col) {
        try (Connection c = dataSource.getConnection()) {
            DatabaseMetaData meta = c.getMetaData();
            // 校验表是否存在
            try (ResultSet tabs = meta.getTables(c.getCatalog(), null, table, new String[]{"TABLE"})) {
                if (!tabs.next()) return false;
            }
            // 校验列是否存在
            try (ResultSet cols = meta.getColumns(c.getCatalog(), null, table, col)) {
                return cols.next();
            }
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