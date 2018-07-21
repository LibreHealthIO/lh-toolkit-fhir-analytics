package org.librehealth.fhir.platform.annotation;

import org.springframework.data.cassandra.core.mapping.SASI;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@SASI(indexMode = SASI.IndexMode.CONTAINS)
@SASI.NonTokenizingAnalyzed(caseSensitive = false)
public @interface SASIIndex {
}
