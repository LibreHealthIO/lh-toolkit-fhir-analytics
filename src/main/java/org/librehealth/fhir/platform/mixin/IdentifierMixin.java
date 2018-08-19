package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Identifier.IdentifierUse;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.UriType;

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