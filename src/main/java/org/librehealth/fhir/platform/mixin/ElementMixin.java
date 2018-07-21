package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Element;
import org.hl7.fhir.dstu3.model.Extension;
import org.hl7.fhir.dstu3.model.StringType;

public interface ElementMixin {
  /**
   * @see Element#getExtensionFirstRep()
   */
  @JsonIgnore
  Extension getExtensionFirstRep();

  /**
   * @see Element#getIdElement()
   */
  @JsonIgnore
  StringType getIdElement();

  /**
   * @see Element#getId()
   */
  @JsonIgnore
  String getId();
}
