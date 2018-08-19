package org.librehealth.fhir.platform.repository;

import org.librehealth.fhir.platform.model.CEncounter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EncounterRepository extends ReactiveCrudRepository<CEncounter, String> {
}
