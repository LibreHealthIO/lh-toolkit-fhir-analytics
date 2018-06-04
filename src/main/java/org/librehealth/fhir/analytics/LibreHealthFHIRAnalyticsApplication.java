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

package org.librehealth.fhir.analytics;

import com.datastax.driver.core.Session;
import org.librehealth.fhir.analytics.cassandra.CassandraDataStoreService;
import org.librehealth.fhir.analytics.cassandra.CassandraDataStoreServiceImpl;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LibreHealthFHIRAnalyticsApplication implements WebMvcConfigurer {
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
			http.authorizeRequests().antMatchers("/css/**").permitAll().anyRequest()
					.fullyAuthenticated().and().formLogin().loginPage("/login")
					.failureUrl("/login?error").permitAll().and().logout().permitAll();
			http.csrf().disable();
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(LibreHealthFHIRAnalyticsApplication.class, args);
		LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
		CassandraDataStoreService cassandraDataStoreService = new CassandraDataStoreServiceImpl();
		Session session = manager.getCassandraConnector().openSession();
		cassandraDataStoreService.init(session);
		try {
			cassandraDataStoreService.insertSampleData(session);
		} catch (LibreHealthFHIRAnalyticsException e) {
			logger.error("Error while loading data to cassandra", e);
		}
		cassandraDataStoreService.preloadData(manager.getJavaSparkContext(), manager.getSparkSession());
	}

}
