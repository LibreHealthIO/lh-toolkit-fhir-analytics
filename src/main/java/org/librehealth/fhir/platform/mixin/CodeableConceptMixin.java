package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.StringType;

public interface CodeableConceptMixin {

  /**
   * @see CodeableConcept#getCodingFirstRep()
   */
  @JsonIgnore
  Coding getCodingFirstRep();

  /**
   * @see CodeableConcept#getTextElement()
   */
  @JsonIgnore
  StringType getTextElement();
}
