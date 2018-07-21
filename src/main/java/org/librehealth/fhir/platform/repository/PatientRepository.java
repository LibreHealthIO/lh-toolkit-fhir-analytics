package org.librehealth.fhir.platform.repository;

import org.librehealth.fhir.platform.model.CPatient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends ReactiveCrudRepository<CPatient, String> {

}
