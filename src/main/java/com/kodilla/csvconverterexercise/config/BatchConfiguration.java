package com.kodilla.csvconverterexercise.config;

import com.kodilla.csvconverterexercise.domain.PersonIn;
import com.kodilla.csvconverterexercise.domain.PersonOut;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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

    @Bean
    PersonProcessor processor() {
        return new PersonProcessor();
    }

    @Bean
    FlatFileItemWriter<PersonOut> writer() {
        //extractor
        BeanWrapperFieldExtractor<PersonOut> extractor = new BeanWrapperFieldExtractor<>();
        //set field names
        extractor.setNames(new String[] {"name", "lastName", "age"});

        //set line aggregator to write item to a line
        DelimitedLineAggregator<PersonOut> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(extractor);


        FlatFileItemWriter<PersonOut> writer = new FlatFileItemWriter<>();
        //writer target
        writer.setResource(new FileSystemResource("output.csv"));
        writer.setShouldDeleteIfExists(true);
        writer.setLineAggregator(aggregator);

        return writer;
    }

    @Bean
    Step calculateAge(
            ItemReader<PersonIn> reader,
            ItemProcessor<PersonIn, PersonOut> processor,
            ItemWriter<PersonOut> writer) {

        return new StepBuilder("calculateAge", jobRepository)
                .<PersonIn , PersonOut>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
