package com.ohgianni.tin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TinApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinApplication.class, args);
	}
}
