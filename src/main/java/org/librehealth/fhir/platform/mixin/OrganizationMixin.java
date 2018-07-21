package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Organization;

public interface OrganizationMixin {

  /**
   * @see Organization#getPartOfTarget()
   */
  @JsonIgnore
  Organization getPartOfTarget();
}
