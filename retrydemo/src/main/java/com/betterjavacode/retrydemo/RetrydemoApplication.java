package com.betterjavacode.retrydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@SpringBootApplication
@EnableRetry
@EnableJpaRepositories(basePackages = "com.betterjavacode.retrydemo.daos")
public class RetrydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetrydemoApplication.class, args);
	}

	@Bean
	public RetryTemplate retryTemplate()
	{
		RetryTemplate retryTemplate = new RetryTemplate();

		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(100);

		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
		simpleRetryPolicy.setMaxAttempts(2);

		retryTemplate.setRetryPolicy(simpleRetryPolicy);
		retryTemplate.setBackOffPolicy(backOffPolicy);
		return retryTemplate;
	}


}
