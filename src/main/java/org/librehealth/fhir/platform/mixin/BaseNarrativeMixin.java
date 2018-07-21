package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.BaseNarrative;

public interface BaseNarrativeMixin {

  /**
   * @see BaseNarrative#getDivAsString()
   */
  @JsonIgnore
  String getDivAsString();

  /**
   * @see BaseNarrative#getStatusAsString()
   */
  @JsonIgnore
  String getStatusAsString();
}
