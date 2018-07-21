package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Narrative;

public interface NarrativeMixin {

  /**
   * @see Narrative#getStatusElement()
   */
  @JsonIgnore
  Enumeration<Narrative.NarrativeStatus> getStatusElement();
}
