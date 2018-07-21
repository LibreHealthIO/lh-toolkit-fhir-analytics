package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Identifier.IdentifierUse;

public interface IdentifierMixin {

  /**
   * @see Identifier#getAssigner()
   */
  @JsonBackReference
  Reference getAssigner();

  /**
   * @see Identifier#getAssignerTarget()
   */
  @JsonIgnore
  Organization getAssignerTarget();

  /**
   * @see Identifier#getUseElement()
   */
  @JsonIgnore
  Enumeration<IdentifierUse> getUseElement();

  /**
   * @see Identifier#getValueElement()
   */
  @JsonIgnore
  StringType getValueElement();

  /**
   * @see Identifier#getSystemElement()
   */
  @JsonIgnore
  UriType getSystemElement();
}