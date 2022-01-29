package com.betterjavacode.springcloudfunctiondemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionalSpringApplication;

@SpringBootApplication
public class SpringcloudfunctiondemoApplication {

	public static void main(String[] args) {
		FunctionalSpringApplication.run(SpringcloudfunctiondemoApplication.class, args);
	}

}
