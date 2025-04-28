package com.ssafy.mvc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ssafy.mvc.model.dao")

public class DBConfig {
//	@Bean
//    DataSource datasource() {
//        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/juston")
//                .username("ssafy")
//                .password("ssafy")
//                .build();
//    }
}
