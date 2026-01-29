package com.example.demo.service;

import com.example.demo.bean.CheckRequest;
import java.util.List;
import java.util.Map;

public interface BadDataService {

    /**
     * 查询表中不符合规则的数据
     * @param tableName 表名
     * @param columnRules 列规则列表
     * @return 违规数据列表（包含主键和列值）
     */
    List<Map<String, Object>> listBadData(String tableName, List<CheckRequest.ColumnRule> columnRules);

    /**
     * 校验表名和列名是否合法
     * @param table 表名
     * @param col 列名
     * @return 是否合法
     */
    boolean isLegalTableColumn(String table, String col);

    /**
     * 获取表的主键列名
     * @param table 表名
     * @return 主键列名
     */
    String getPrimaryKey(String table);
}