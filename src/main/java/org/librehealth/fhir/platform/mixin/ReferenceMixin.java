package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.StringType;

public interface ReferenceMixin {

  /**
   * @see Reference#getIdentifier()
   */
  @JsonManagedReference
  Identifier getIdentifier();

  /**
   * @see Reference#getDisplayElement()
   */
  @JsonIgnore
  StringType getDisplayElement();

  /**
   * @see Reference#getReferenceElement_()
   */
  @JsonIgnore
  StringType getReferenceElement_();
}
