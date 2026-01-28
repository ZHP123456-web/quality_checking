package com.example.demo.bean;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CheckRequest {
    @NotBlank
    private String tableName;
    @NotBlank
    private String column;
    @NotBlank
    private String rule;   // JSON
}