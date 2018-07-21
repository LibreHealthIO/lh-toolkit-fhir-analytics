package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.UriType;

public interface ResourceMixin {

  /**
   * @see Resource#getId()
   */
  @JsonIgnore
  IdType getId();

  /**
   * @see Resource#getIdElement()
   */
  @JsonIgnore
  IdType getIdElement();

  /**
   * @see Resource#getLanguageElement()
   */
  @JsonIgnore
  CodeType getLanguageElement();

  /**
   * @see Resource#getImplicitRulesElement()
   */
  @JsonIgnore
  UriType getImplicitRulesElement();

}
