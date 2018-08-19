package org.librehealth.fhir.platform.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.QueryLogger;
import lombok.RequiredArgsConstructor;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.convert.CustomConversions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("NullableProblems")
@RequiredArgsConstructor
@Configuration
public class CassandraConfig {

  private final ApplicationContext applicationContext;

  @Value("${spring.data.cassandra.keyspace-name}")
  private String KEYSPACE;

  @Bean
  public CassandraClusterFactoryBean cluster() {
    CassandraCqlClusterFactoryBean clusterFactoryBean = new CassandraCqlClusterFactoryBean();
    clusterFactoryBean.setKeyspaceCreations(getKeyspaceCreations());
    return clusterFactoryBean;
  }

  @Bean
  public QueryLogger queryLogger(Cluster cluster) {
    QueryLogger queryLogger = QueryLogger.builder().build();
    cluster.register(queryLogger);
    return queryLogger;
  }

  private List<CreateKeyspaceSpecification> getKeyspaceCreations() {
    return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(KEYSPACE)
            .ifNotExists(true));
  }

  @Bean
  public CassandraCustomConversions customConversions() {
    return new CassandraCustomConversions(new ArrayList<>(
            applicationContext.getBeansWithAnnotation(CassandraConverter.class).values()
    ));
  }

  @Bean
  public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
    CustomCassandraMappingContext mappingContext = new CustomCassandraMappingContext();

    mappingContext.setInitialEntitySet(getInitialEntitySet());

    CustomConversions customConversions = customConversions();

    mappingContext.setCustomConversions(customConversions);
    mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
    mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), KEYSPACE));

    return mappingContext;
  }

  private Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
    return CassandraEntityClassScanner.scan("org.librehealth.fhir.platform.model");
  }

}
