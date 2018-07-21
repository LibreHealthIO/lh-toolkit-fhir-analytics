package org.librehealth.fhir.platform.handler;

import lombok.RequiredArgsConstructor;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.OperationOutcome;
import org.librehealth.fhir.platform.model.CPatient;
import org.librehealth.fhir.platform.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.beans.FeatureDescriptor;
import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PatientHandler {

  private final PatientRepository patientRepository;
  private final ReactiveCassandraOperations reactiveCassandraOperations;

  public Mono<ServerResponse> update(ServerRequest request) {
    String id = request.pathVariable("id");
    Mono<CPatient> updatedPatientMono = request.bodyToMono(CPatient.class)
            .doOnNext(patient -> patient.setPatientId(id));
    return patientRepository
            .findById(id)
            .flatMap(patient -> ServerResponse.ok()
                    .body(
                            updatedPatientMono
                                    .flatMap(updatedPatient -> {
                                      BeanUtils.copyProperties(updatedPatient, patient, getNullProperties(updatedPatient));
                                      return patientRepository.save(updatedPatient);
                                    }),
                            CPatient.class))
            .switchIfEmpty(ServerResponse
                    .created(URI.create("http://localhost:8080/patient/" + id))
                    .lastModified(ZonedDateTime.now())
                    .body(patientRepository.saveAll(updatedPatientMono), CPatient.class));
  }

  public Mono<ServerResponse> save(ServerRequest request) {
    Mono<CPatient> patientMono = request
            .bodyToMono(CPatient.class)
            .doOnNext(patient -> patient.setId(IdType.newRandomUuid()));
    return ServerResponse.ok()
            .body(patientRepository.saveAll(patientMono), CPatient.class);
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    String id = request.pathVariable("id");
    return ServerResponse.noContent()
            .build(patientRepository.deleteById(id));
  }

  public Mono<ServerResponse> getById(ServerRequest request) {
    String id = request.pathVariable("id");
    return patientRepository
            .findById(id)
            .flatMap(patient -> ServerResponse.ok().body(Mono.just(patient), CPatient.class))
            .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> search(ServerRequest request) {
    MultiValueMap<String, String> params = request.queryParams();

    if (params.size() == 0) {
      // Return all entities if no search parameters specified
      return ServerResponse.ok()
              .body(patientRepository.findAll(), CPatient.class);
    }

    try {
      List<CriteriaDefinition> criteriaDefinitions = new ArrayList<>();
      for (Map.Entry<String, String> entry :
              params.toSingleValueMap().entrySet()) {
        if (entry.getValue() == null) {
          throw new IllegalStateException("Search parameter value cannot be null");
        }
        criteriaDefinitions
                .add(getCriteriaDefinition(entry.getKey(), entry.getValue()));
      }

      reactiveCassandraOperations.select(Query.empty(), CPatient.class).subscribe(System.out::println);

      Flux<CPatient> patients = reactiveCassandraOperations
              .select(Query.query(criteriaDefinitions).withAllowFiltering(), CPatient.class);
      return ServerResponse.ok()
              .body(patients, CPatient.class);
    } catch (IllegalStateException ex) {
      return ServerResponse.badRequest().body(BodyInserters.fromObject(ex.getMessage()));
    }
  }

  // FIXME: 19-Jul-18 Some of the properties do not work with search
  // Throwing an error status 500 with `No property <property-name> found on org.openmrs.model.CPatient!`
  private CriteriaDefinition getCriteriaDefinition(String key, String value) {
    Criteria criteria = Criteria.where(key);
    switch (key.toLowerCase()) {
      case "_id":
        criteria = Criteria.where("patientid");
        return criteria.is(value);
      case "address":
      case "communication":
      case "contact":
      case "contained":
      case "generalpractitioner":
      case "identifier":
      case "link":
      case "name":
      case "telecom":
      case "photo":
        return criteria.contains(value);
      case "id":
      case "animal":
      case "deceased":
      case "language":
      case "managingorganization":
      case "multiplebirth":
        // TODO: 12-Jul-18 Compare the supplied value with the target key instead of the whole JSON object
        return criteria.like(value);
      case "active":
      case "gender":
        return criteria.is(value);
      case "birthdate":
        return criteria.is(Date.from(Instant.parse(value)));
      default:
        throw new IllegalStateException("Unknown search parameter passed: " + key);
    }
  }

  private OperationOutcome getOperationOutcome(OperationOutcome.IssueSeverity severity, OperationOutcome.IssueType type, String diagnostics) {
    OperationOutcome outcome = new OperationOutcome();
    OperationOutcome.OperationOutcomeIssueComponent issue = new OperationOutcome.OperationOutcomeIssueComponent();
    issue.setSeverity(severity);
    issue.setCode(type);
    issue.setDiagnostics(diagnostics);
    outcome.setIssue(Collections.singletonList(issue));
    return outcome;
  }

  private String[] getNullProperties(Object bean) {
    BeanWrapper wrapper = new BeanWrapperImpl(bean);
    return Stream.of(wrapper.getPropertyDescriptors())
            .map(FeatureDescriptor::getName)
            .filter(s -> wrapper.getPropertyValue(s) == null)
            .toArray(String[]::new);
  }

}
