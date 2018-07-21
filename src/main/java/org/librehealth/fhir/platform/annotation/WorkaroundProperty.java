package org.librehealth.fhir.platform.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.cassandra.core.mapping.Column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@JacksonAnnotationsInside
@JsonIgnore
@Column
public @interface WorkaroundProperty {

  /**
   * @see Column#value()
   */
  @AliasFor(annotation = Column.class)
  String value() default "";

}
