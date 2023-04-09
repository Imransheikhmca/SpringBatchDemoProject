package com.batchdemo.springbatch.repository;

import com.batchdemo.springbatch.entity.BusinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessSurveyRepository extends JpaRepository<BusinessSurvey, Integer> {


}
