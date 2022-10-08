package com.example.movieweb.movie;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class MovieBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<Movie> movieItemReader() {

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("title", "actor", "year");

        DefaultLineMapper<Movie> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new MovieFieldSetMapper());

        FlatFileItemReader<Movie> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource("/movies.txt"));
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Movie> movieItemWriter() {

        JdbcBatchItemWriter<Movie> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("" +
                "insert into `movies` (`title`, `actor`, `year`) " +
                "values (:title, :actor, :year)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Movie, Movie>chunk(10)
                .reader(movieItemReader())
                .writer(movieItemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
