package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.Location;

public interface EncounterHospitalizationComponentMixin {

  /**
   * @see Encounter.EncounterHospitalizationComponent#getOriginTarget()
   */
  @JsonIgnore
  Location getOriginTarget();

  /**
   * @see Encounter.EncounterHospitalizationComponent#getDestinationTarget()
   */
  @JsonIgnore
  Location getDestinationTarget();
}
