package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Period;

public interface PeriodMixin {

  /**
   * @see Period#getStartElement()
   */
  @JsonIgnore
  DateTimeType getStartElement();

  /**
   * @see Period#getEndElement()
   */
  @JsonIgnore
  DateTimeType getEndElement();
}