package com.batchdemo.springbatch.config;

import com.batchdemo.springbatch.entity.BusinessSurvey;
import com.batchdemo.springbatch.repository.BusinessSurveyRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class StringBatchConfig {

    private JobBuilder jobBuilderFactory;

    private StepBuilder stepBuilder;

    private BusinessSurveyRepository businessSurveyRepository;


    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    /**
     * To read from CSV file. (class provided by spring)
     * @return
     */
    @Bean
    public FlatFileItemReader<BusinessSurvey> reader(){

        FlatFileItemReader<BusinessSurvey> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src\\main\\resources\\businesssurvey.csv"));
        //any name
        itemReader.setName("csvReader");

        //first line is label. therefore ignore this.
        itemReader.setLinesToSkip(1);

        //telling how to map csv to object.
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<BusinessSurvey> lineMapper() {
        DefaultLineMapper<BusinessSurvey> lineMapper = new DefaultLineMapper<>();

        //the class which will map csv to object.
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        //this header is set to the tokenizer
        delimitedLineTokenizer.setNames("Year","Industry_aggregation_NZSIOC","Industry_code_NZSIOC","Industry_name_NZSIOC","Units","Variable_code","Variable_name","Variable_category","Value","Industry_code_ANZSIC06");

        //this class is mapping delimitedLineTokenizer(csv data) to LinerMapper(object).
        BeanWrapperFieldSetMapper<BusinessSurvey> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BusinessSurvey.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;

    }

    /**
     * this bean is used for processing the csv file. i.e. apart from reading and writing also
     * @return
     */
    @Bean
    public BusinessSurveyProcessor processor(){
        return new BusinessSurveyProcessor();
    }

    @Bean
    public RepositoryItemWriter<BusinessSurvey> writer(){
        RepositoryItemWriter<BusinessSurvey> writer = new RepositoryItemWriter<>();
        writer.setRepository(businessSurveyRepository);
        writer.setMethodName("save");
        return writer;
    }


    /**
     * this is the step object will need to give this step object to job.
     * @return
     */
    @Bean
    public Step step1(){
        return new StepBuilder("csv-step", jobRepository)
                .<BusinessSurvey, BusinessSurvey>chunk(10,transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    /**
     *
     */

}
