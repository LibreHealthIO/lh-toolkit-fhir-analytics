package org.librehealth.fhir.platform.config;

import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.util.TypeInformation;

public class CustomCassandraMappingContext extends CassandraMappingContext {

  /**
   * Create persistent entites for classes annotated with org.springframework.data.cassandra.core.mapping.Table
   */
  @Override
  protected boolean shouldCreatePersistentEntityFor(TypeInformation<?> typeInfo) {
    if (typeInfo.getType().isAnnotationPresent(Table.class)) {
      return true;
    }
    return super.shouldCreatePersistentEntityFor(typeInfo);
  }
}
