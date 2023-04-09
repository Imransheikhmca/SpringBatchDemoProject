package com.batchdemo.springbatch.config;

import com.batchdemo.springbatch.entity.BusinessSurvey;
import org.springframework.batch.item.ItemProcessor;

public class BusinessSurveyProcessor implements ItemProcessor<BusinessSurvey, BusinessSurvey> {
    @Override
    public BusinessSurvey process(BusinessSurvey businessSurvey) throws Exception {
        return businessSurvey;
    }
}
