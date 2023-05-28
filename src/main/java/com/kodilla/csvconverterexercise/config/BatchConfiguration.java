package com.kodilla.csvconverterexercise.config;

import com.kodilla.csvconverterexercise.domain.PersonIn;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    FlatFileItemReader<PersonIn> reader() {
        FlatFileItemReader<PersonIn> reader = new FlatFileItemReader<>();
        //indicate source
        reader.setResource(new ClassPathResource("input.csv"));

        //indicate how to divide lines with delimiter
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        //set field names
        tokenizer.setNames("name", "lastName", "dateOfBirth");

        //indicate how to map values onto object fields
        BeanWrapperFieldSetMapper<PersonIn> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(PersonIn.class);

        //setup lineMapper
        DefaultLineMapper<PersonIn> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }
}
