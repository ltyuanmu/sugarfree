package com.sugarfree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Calendar;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan("com.sugarfree" )
public class SugarfreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SugarfreeApplication.class, args);
	}
}
