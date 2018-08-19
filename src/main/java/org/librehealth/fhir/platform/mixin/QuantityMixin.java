package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.DecimalType;
import org.hl7.fhir.dstu3.model.Quantity;

public interface QuantityMixin {

  /**
   * @see Quantity#getValueElement()
   */
  @JsonIgnore
  DecimalType getValueElement();
}
