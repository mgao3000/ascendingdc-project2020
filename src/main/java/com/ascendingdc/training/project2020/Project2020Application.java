package com.ascendingdc.training.project2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@ServletComponentScan("com.ascendingdc.training.project2020.filter")
@ServletComponentScan
/*
 * The following is for development using spring boot embedded Tomcat
 */
//public class Project2020Application {
//
//	public static void main(String[] args) {
//		SpringApplication.run(Project2020Application.class, args);
//	}
//}

/*
 *  The following is for deploy the app to external Tomcat
 */
public class Project2020Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Project2020Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Project2020Application.class);
	}

}
