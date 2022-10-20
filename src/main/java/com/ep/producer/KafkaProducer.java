package com.ep.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.ep.entity.SampleEvent;

@Component
public class KafkaProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	@Value("${topic.name.producer}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, SampleEvent> kafkaTemplate;

	public void send(SampleEvent sampleEvent) {

		// send event asynchronously and return a future
		ListenableFuture<SendResult<String, SampleEvent>> future = kafkaTemplate.send(topicName, sampleEvent);

		// register callback with listener to receive the result of send asynchronously
		future.addCallback(new ListenableFutureCallback<SendResult<String, SampleEvent>>() {

			@Override
			public void onSuccess(SendResult<String, SampleEvent> result) {
				LOGGER.info("sent event='{}' with offset={}", sampleEvent, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.error("unable to send event='{}'", sampleEvent, ex);
			}
		});
	}
}
