package org.librehealth.fhir.platform.mixin;

import ca.uhn.fhir.context.FhirVersionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.BaseResource;

public interface BaseResourceMixin {

  /**
   * @see BaseResource#getStructureFhirVersionEnum()
   */
  @JsonIgnore
  FhirVersionEnum getStructureFhirVersionEnum();

}
