package com.sugarfree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.sugarfree" )
public class SugarfreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SugarfreeApplication.class, args);
	}
}
