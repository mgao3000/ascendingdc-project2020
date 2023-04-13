package com.ascendingdc.training.project2020.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
//@ComponentScan("com.ascendingdc.training.project2020")  -- no need here since our spring boot start app is under com.ascendingdc.training.project2020
public class SpringJdbcTestConfig {

//    @Autowired
//    Environment environment;
//
//    private final String URL = "url";
//    private final String USER = "dbuser";
//    private final String DRIVER = "driver";
//    private final String PASSWORD = "dbpassword";
//
//    @Bean
//    DataSource dataSource() {
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setUrl(environment.getProperty(URL));
//        driverManagerDataSource.setUsername(environment.getProperty(USER));
//        driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
//        driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
//        return driverManagerDataSource;
//    }

    @Bean
    public DataSource postgreSqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pilot");
        dataSource.setUsername("admin");
        dataSource.setPassword("password");
        

        return dataSource;
    }
}
