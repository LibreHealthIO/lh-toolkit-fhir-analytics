package org.librehealth.fhir.platform.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.IQuery;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.DomainResource;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.librehealth.fhir.platform.model.CEncounter;
import org.librehealth.fhir.platform.model.CObservation;
import org.librehealth.fhir.platform.model.CPatient;
import org.librehealth.fhir.platform.repository.EncounterRepository;
import org.librehealth.fhir.platform.repository.ObservationRepository;
import org.librehealth.fhir.platform.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CassandraDataLoader implements CommandLineRunner {

  private final PatientRepository patientRepository;
  private final ObservationRepository observationRepository;
  private final EncounterRepository encounterRepository;
  private final FhirContext fhirContext = FhirContext.forDstu3();

  @Value("${fhir.server.url}")
  private String server;

  @Value("${fhir.server.resource.count}")
  private int count;

  @Override
  public void run(String... args) throws Exception {
    IGenericClient client = fhirContext.newRestfulGenericClient(server);
    Bundle patientBundle = getData(client, count, Patient.class);
    List<CPatient> patients = patientBundle
            .getEntry()
            .stream()
            .map(entry -> castToTargetResource(entry.getResource(), new CPatient()))
            .collect(Collectors.toList());
    patientRepository.saveAll(patients).subscribe();
    Bundle observationBundle = getData(client, count, Observation.class);
    List<CObservation> observations = observationBundle
            .getEntry()
            .stream()
            .map(entry -> castToTargetResource(entry.getResource(), new CObservation()))
            .collect(Collectors.toList());
    observationRepository.saveAll(observations).subscribe();
    Bundle encounterBundle = getData(client, count, Encounter.class);
    List<CEncounter> encounters = encounterBundle
            .getEntry()
            .stream()
            .map(entry -> castToTargetResource(entry.getResource(), new CEncounter()))
            .collect(Collectors.toList());
    encounterRepository.saveAll(encounters).subscribe();
  }

  /**
   * Get a bundle of resources of the given type matching the criteria and count.
   *
   * @param client Client object used to execute the query
   * @param count  No. of entries in the bundle
   * @param type   Type of resource to retrieve and return
   * @return bundle of resources of type.
   */
  public Bundle getData(IGenericClient client, int count, Class<? extends DomainResource> type) {
    IQuery<Bundle> query = client.search().forResource(type)
            .count(count)
            .returnBundle(Bundle.class);
    return query.execute();
  }

  private <S, T> T castToTargetResource(S source, T target) {
    BeanUtils.copyProperties(source, target);
    return target;
  }
}
