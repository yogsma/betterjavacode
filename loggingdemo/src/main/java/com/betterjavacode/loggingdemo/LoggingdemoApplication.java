package com.betterjavacode.loggingdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoggingdemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingdemoApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Before starting the application........");
		SpringApplication.run(LoggingdemoApplication.class, args);
		LOGGER.info("After starting the application.........");
	}

}
