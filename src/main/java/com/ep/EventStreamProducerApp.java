package com.ep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, XADataSourceAutoConfiguration.class })
public class EventStreamProducerApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventStreamProducerApp.class);

	public static void main(String[] args) {
		LOGGER.info("Statring Event Stream Producer Application");
		SpringApplication.run(EventStreamProducerApp.class, args);
	}
}
