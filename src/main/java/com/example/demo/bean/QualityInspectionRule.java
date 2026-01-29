package com.example.demo.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "quality_inspection_rule", autoResultMap = true)
public class QualityInspectionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "UNID", type = IdType.INPUT)
    private String unid;

    /** 规则名称 */
    @TableField("RULE_NAME")
    private String ruleName;

    /** 关联字段 */
    @TableField("REL_COLUMN")
    private String relColumn;

    /** 校验规则（JSON） */
    @TableField(value = "RULE", typeHandler = JacksonTypeHandler.class)
    private Object rule;   // 也可改成自定义 VO 如 RuleJson

    /** 校验规则说明 */
    @TableField("RULE_MEMO")
    private String ruleMemo;

    /** 所属方案UNID */
    @TableField("PUNID")
    private String punid;

    /** 所属方案名称 */
    @TableField("PNAME")
    private String pname;
}