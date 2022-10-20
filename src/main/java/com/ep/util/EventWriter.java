package com.ep.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ep.entity.SampleEvent;
import com.ep.producer.KafkaProducer;

@Component
public class EventWriter implements ItemWriter<SampleEvent>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventWriter.class);
	
	@Autowired
	KafkaProducer kafkaProducer;

	@Override
	public void write(List<? extends SampleEvent> eventList) throws Exception {
		for (SampleEvent event : eventList) {
			LOGGER.info("Writing Event: {}", event);
			kafkaProducer.send(event);
		}
	}

}
