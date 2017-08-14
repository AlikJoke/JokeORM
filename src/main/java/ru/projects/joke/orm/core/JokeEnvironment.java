package ru.projects.joke.orm.core;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JokeEnvironment {

	@Autowired
	private JokeConfiguration configuration;

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(configuration.getDriverClassname());
		dataSource.setDefaultAutoCommit(configuration.autocommitEnabled());
		dataSource.setUsername(configuration.getUsername());
		dataSource.setPassword(configuration.getPassword());
		dataSource.setUrl(configuration.getUrl());
		return dataSource;
	}
}
