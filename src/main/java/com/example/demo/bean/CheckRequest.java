package com.example.demo.bean;

import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CheckRequest {
    @NotBlank
    private String tableName;

    @Valid
    @NotEmpty
    private List<ColumnRule> columnRules;

    @Data
    public static class ColumnRule {
        @NotBlank
        private String column;

        @NotBlank
        private String rule;   // JSON
    }
}