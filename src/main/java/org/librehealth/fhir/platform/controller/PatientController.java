package org.librehealth.fhir.platform.controller;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.beans.FeatureDescriptor;
import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("Patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;
    private final ReactiveCassandraOperations reactiveCassandraOperations;

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> update(@PathVariable String id, @RequestBody Mono<CPatient> patientMono) {
        Mono<CPatient> updatedPatientMono = patientMono
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

    @GetMapping
    public ResponseEntity<?> search(@RequestParam Map<String, String> params) {

        if (params.size() == 0) {
            // Return all entities if no search parameters specified
            List<CPatient> data = patientRepository.findAll().collectList().block();
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
        }

        try {
            List<CriteriaDefinition> criteriaDefinitions = new ArrayList<>();
            for (Map.Entry<String, String> entry :
                    params.entrySet()) {
                if (entry.getValue() == null) {
                    throw new IllegalStateException("Search parameter value cannot be null");
                }
                criteriaDefinitions
                        .add(getCriteriaDefinition(entry.getKey(), entry.getValue()));
            }

            reactiveCassandraOperations.select(Query.empty(), CPatient.class).subscribe(System.out::println);

            Flux<CPatient> patients = reactiveCassandraOperations
                    .select(Query.query(criteriaDefinitions).withAllowFiltering(), CPatient.class);
            return new ResponseEntity(patients.collectList().block(), new HttpHeaders(), HttpStatus.OK);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BodyInserters.fromObject(ex.getMessage()));
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

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        patientRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        CPatient patient = patientRepository
                .findById(id).block();
        if (patient != null) {
            return new ResponseEntity(patient, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody Mono<CPatient> patientMono) {
        patientMono = patientMono
                .doOnNext(patient -> patient.setId(IdType.newRandomUuid()));
        patientRepository.saveAll(patientMono);
        return new ResponseEntity(HttpStatus.NOT_FOUND);

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
