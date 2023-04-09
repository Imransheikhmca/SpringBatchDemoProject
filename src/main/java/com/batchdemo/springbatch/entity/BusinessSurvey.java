package com.batchdemo.springbatch.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BUSINESS_SURVEY_INFO")
public class BusinessSurvey {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "Industry_aggregation_NZSIOC")
    private String industryAggregationNZSIOC;

    @Column(name = "Industry_code_NZSIOC")
    private String industryCodeNZSIOC;

    @Column(name = "Industry_name_NZSIOC")
    private String industryNameNZSIOC;

    @Column(name = "Units")
    private String units;

    @Column(name = "Variable_code")
    private String variableCode;

    @Column(name = "Variable_name")
    private String variableName;

    @Column(name = "Variable_category")
    private String variableCategory;

    @Column(name = "Value")
    private String value;

    @Column(name = "Industry_code_ANZSIC06")
    private String industryCodeANZSIC06;


}
