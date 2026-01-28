package com.example.demo.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quality_inspection_rule", indexes = {
        @Index(name = "idx_punid", columnList = "PUNID"),
        @Index(name = "idx_table_column", columnList = "REL_TABLE, REL_COLUMN")
})
@TypeDef(name = "json", typeClass = JsonType.class)
public class QualityInspectionRule {

    @Id
    @Column(name = "UNID", length = 32, nullable = false)
    private String unid;

    @Column(name = "RULE_NAME", length = 100, nullable = false)
    private String ruleName;

    @Column(name = "REL_TABLE", length = 50)
    private String relTable;

    @Column(name = "REL_COLUMN", length = 50)
    private String relColumn;

    @Type(type = "json")
    @Column(name = "RULE", columnDefinition = "json", nullable = false)
    private JsonNode rule;

    @Column(name = "RULE_MEMO", length = 255)
    private String ruleMemo;

    @Column(name = "PUNID", length = 50)
    private String punid;

    @Column(name = "PNAME", length = 50)
    private String pname;

    @Column(name = "DELETED")
    private Long deleted = 0L;

    @Column(name = "EXT1", length = 255)
    private String ext1;

    @Column(name = "EXT2", length = 255)
    private String ext2;

    @Column(name = "CREATED_TIME", updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "UPDATED_TIME")
    private LocalDateTime updatedTime;

    // Getters and Setters
    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRelTable() {
        return relTable;
    }

    public void setRelTable(String relTable) {
        this.relTable = relTable;
    }

    public String getRelColumn() {
        return relColumn;
    }

    public void setRelColumn(String relColumn) {
        this.relColumn = relColumn;
    }

    public JsonNode getRule() {
        return rule;
    }

    public void setRule(JsonNode rule) {
        this.rule = rule;
    }

    public String getRuleMemo() {
        return ruleMemo;
    }

    public void setRuleMemo(String ruleMemo) {
        this.ruleMemo = ruleMemo;
    }

    public String getPunid() {
        return punid;
    }

    public void setPunid(String punid) {
        this.punid = punid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}