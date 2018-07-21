package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.BackboneElement;
import org.hl7.fhir.dstu3.model.Extension;

public interface BackboneElementMixin {

  /**
   * @see BackboneElement#getModifierExtensionFirstRep()
   */
  @JsonIgnore
  Extension getModifierExtensionFirstRep();
}
