package org.librehealth.fhir.platform.repository;

import org.librehealth.fhir.platform.model.CObservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ObservationRepository extends ReactiveCrudRepository<CObservation, String> {

}
