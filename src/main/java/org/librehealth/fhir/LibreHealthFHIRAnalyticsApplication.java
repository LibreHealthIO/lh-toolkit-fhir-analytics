/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.librehealth.fhir;

import com.datastax.driver.core.Session;
import org.librehealth.fhir.analytics.LibreHealthFHIRAnalyticsExecutionManager;
import org.librehealth.fhir.analytics.cassandra.CassandraDataService;
import org.librehealth.fhir.analytics.cassandra.CassandraDataServiceImpl;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAutoConfiguration
public class LibreHealthFHIRAnalyticsApplication extends SpringBootServletInitializer implements WebMvcConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(LibreHealthFHIRAnalyticsApplication.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/").setViewName("home");
	}

	@Configuration
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/css/**", "/Patient/**").permitAll().anyRequest()
					.fullyAuthenticated().and().formLogin().loginPage("/login")
					.failureUrl("/login?error").permitAll().and().logout().permitAll();
			http.csrf().disable();
		}

	}

	public static void main(String[] args) {
		logger.info("Running My Application");
		SpringApplication.run(LibreHealthFHIRAnalyticsApplication.class, args);
		init();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		init();
		return application.sources(LibreHealthFHIRAnalyticsApplication.class);
	}

	private static void init() {
		LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
		CassandraDataService cassandraDataStoreService = CassandraDataServiceImpl.getInstance();
		Session session = manager.getCassandraConnector().openSession();
		try {
			cassandraDataStoreService.insertDemoData(session);
		} catch (LibreHealthFHIRAnalyticsException e) {
			logger.error("Error while loading data to cassandra", e);
		}
	}
}
