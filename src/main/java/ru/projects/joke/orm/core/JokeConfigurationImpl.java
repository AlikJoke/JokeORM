package ru.projects.joke.orm.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JokeConfigurationImpl implements JokeConfiguration {

	@Value("${joke.orm.datasource.url}")
	private String url;

	@Value("${joke.orm.datasource.username}")
	private String username;

	@Value("${joke.orm.datasource.password}")
	private String password;

	@Value("${joke.orm.datasource.autocommit}")
	private boolean autocommit;

	@Value("${joke.orm.datasource.driver.class_name}")
	private String driverClassname;

	@Value("${joke.orm.build.schema}")
	private boolean buildSchema;

	public JokeConfigurationImpl() {
		super();
	}

	public JokeConfigurationImpl(String url, String username, String password, String driverClassname) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClassname = driverClassname;
	}

	@Override
	public String getDriverClassname() {
		return this.driverClassname;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean autocommitEnabled() {
		return this.autocommit;
	}

	@Override
	public boolean buildSchemaEnabled() {
		return this.buildSchema;
	}

	@Override
	public String getUrl() {
		return this.url;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

}
