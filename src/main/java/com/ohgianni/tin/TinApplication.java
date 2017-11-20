package com.ohgianni.tin;

import com.ohgianni.tin.Entity.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TinApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinApplication.class, args);
	}

}
