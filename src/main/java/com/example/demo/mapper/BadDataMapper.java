package com.example.demo.mapper;

import com.example.demo.bean.CheckRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface BadDataMapper {

    /**
     * 查询不符合正则规则的数据
     * @param tableName 表名
     * @param column 检查的列名
     * @param pattern 正则表达式
     * @return 全部列的列表
     */
    @Select("SELECT * FROM ${tableName} WHERE ${column} NOT REGEXP #{pattern}")
    List<Map<String, Object>> findBadDataByRegex(
            @Param("tableName") String tableName,
            @Param("column") String column,
            @Param("pattern") String pattern
    );

    /**
     * 多条件且查询不符合正则规则的数据
     * @param tableName 表名
     * @param columnRules 列规则列表
     * @return 全部列的列表
     */
    @Select({
            "<script>",
            "SELECT * FROM `${tableName}`",
            "WHERE 1=0",
            "<foreach collection='columnRules' item='cr'>",
            "OR `${cr.column}` NOT REGEXP #{cr.rule}",
            "</foreach>",
            "</script>"
    })
    List<Map<String, Object>> findBadDataByMultipleRegex(
            @Param("tableName") String tableName,
            @Param("columnRules") List<CheckRequest.ColumnRule> columnRules
    );
}