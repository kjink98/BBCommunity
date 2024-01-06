package com.bbcommunity;
/*
* 이 클래스는 Spring Boot를 사용하여 데이터베이스 연결을 구성하는 클래스입니다.
* @Configuration 어노테이션을 통해 이 클래스가 스프링의 설정 파일임을 나타냅니다.
* @PropertySource를 통해 설정 정보를 가져올 속성 파일의 위치를 지정합니다.
*/
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
/*
* 이 클래스에서는 HikariCP라는 데이터베이스 연결 풀 라이브러리를 사용합니다.
* HikariCP는 빠르고, 간단하며, 신뢰성이 높아 널리 사용되는 라이브러리입니다.
*/
public class DatabaseConfiguration {
	/*
	* hikariConfig() 메서드는 HikariCP의 설정을 담당합니다.
	* @ConfigurationProperties 어노테이션을 통해 
	* 'spring.datasource.hikari'라는 prefix를 가진 설정을 읽어옵니다.
	*/
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	/*
	* dataSource() 메서드는 실제 데이터베이스 연결 풀을 생성합니다.
	* HikariConfig를 인자로 받아, HikariDataSource를 생성하고 이를 반환합니다.
	*/
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		return dataSource;
	}
}
