package com.ep.util;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

import com.ep.entity.SampleEvent;

@Component
public class EventFileReader extends FlatFileItemReader<SampleEvent> implements ItemReader<SampleEvent> {
	
	public EventFileReader() {
		setLinesToSkip(1);
		setLineMapper(getDefaultLineMapper());
	}

	public DefaultLineMapper<SampleEvent> getDefaultLineMapper() {
		DefaultLineMapper<SampleEvent> defaultLineMapper = new DefaultLineMapper<SampleEvent>();

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(new String[] { "name", "age" });
		delimitedLineTokenizer.setDelimiter(",");
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

		BeanWrapperFieldSetMapper<SampleEvent> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SampleEvent>();
		
		beanWrapperFieldSetMapper.setTargetType(SampleEvent.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		return defaultLineMapper;
	}
}
