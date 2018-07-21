package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.BaseReference;
import org.hl7.fhir.instance.model.api.IIdType;

public interface BaseReferenceMixin {

  /**
   * @see BaseReference#getReferenceElement()
   */
  @JsonIgnore
  IIdType getReferenceElement();
}
