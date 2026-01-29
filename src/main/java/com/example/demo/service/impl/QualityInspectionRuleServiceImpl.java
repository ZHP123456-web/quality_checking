package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.bean.QualityInspectionRule;
import com.example.demo.mapper.QualityInspectionRuleMapper;
import com.example.demo.service.IQualityInspectionRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class QualityInspectionRuleServiceImpl
        extends ServiceImpl<QualityInspectionRuleMapper, QualityInspectionRule>
        implements IQualityInspectionRuleService {
}