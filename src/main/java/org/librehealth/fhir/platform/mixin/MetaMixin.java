package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.InstantType;
import org.hl7.fhir.dstu3.model.Meta;

public interface MetaMixin {

  /**
   * @see Meta#getLastUpdatedElement()
   */
  @JsonIgnore
  InstantType getLastUpdatedElement();

  /**
   * @see Meta#getVersionIdElement()
   */
  @JsonIgnore
  IdType getVersionIdElement();

  /**
   * @see Meta#getSecurityFirstRep()
   */
  @JsonIgnore
  Coding getSecurityFirstRep();

  /**
   * @see Meta#getTagFirstRep()
   */
  @JsonIgnore
  Coding getTagFirstRep();
}
