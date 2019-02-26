package com.trip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.trip.mapper")
public class AtripApplication {

	public static void main(String[] args) {

		SpringApplication.run(AtripApplication.class, args);
	}


}
