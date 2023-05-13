package com.ascendingdc.training.project2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@ServletComponentScan("com.ascendingdc.training.project2020.filter")
//@ServletComponentScan
public class Project2020Application {

	public static void main(String[] args) {
		SpringApplication.run(Project2020Application.class, args);
	}
}

//public class Project2020Application extends SpringBootServletInitializer {
//
//	public static void main(String[] args) {
//		SpringApplication.run(Project2020Application.class, args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(Project2020Application.class);
//	}
//
//}
