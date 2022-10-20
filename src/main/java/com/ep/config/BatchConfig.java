package com.ep.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.ep.entity.SampleEvent;
import com.ep.util.EventFileReader;
import com.ep.util.EventWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	EventFileReader eventFileReader;

	@Autowired
	EventWriter eventWriter;

	@Bean
	public Job createJob() {
		return jobBuilderFactory.get("MyJob").incrementer(new RunIdIncrementer()).flow(createStep()).end().build();
	}

	@Bean
	public Step createStep() {
		return stepBuilderFactory.get("MyStep").<SampleEvent, SampleEvent>chunk(1).reader(reader()).writer(eventWriter)
				.build();
	}

	@Bean
	public ItemReader<SampleEvent> reader() {
		Resource[] resources = null;
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		try {
			resources = patternResolver.getResources("file:./data/*.csv");
		} catch (IOException e) {
			LOGGER.error("Error getting File. {}", e);
		}

		MultiResourceItemReader<SampleEvent> reader = new MultiResourceItemReader<>();
		reader.setResources(resources);
		reader.setDelegate(eventFileReader);
		return reader;
	}
}
