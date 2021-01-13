package com.atos.tutorial.at.configuration;

import com.atos.tutorial.at.util.RestUtil;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverScreenshotOnFailure;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


@Configuration
@ComponentScan({"com.wipro.tutorial.at", "templates"})
@PropertySource("classpath:properties/${environment}/application.properties")
public class ProjectConfiguration implements InitializingBean {

	@Autowired
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer getPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		RestUtil.configureProxy(env);
	}
}
