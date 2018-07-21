package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.StringType;

public interface StringTypeMixin {
  /**
   * @see StringType#getValueNotNull()
   */
  @JsonIgnore
  String getValueNotNull();
}
