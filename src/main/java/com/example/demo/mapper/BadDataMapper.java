package com.example.demo.mapper;

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
}