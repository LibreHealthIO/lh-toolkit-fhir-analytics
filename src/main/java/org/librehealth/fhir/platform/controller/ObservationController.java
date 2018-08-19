package org.librehealth.fhir.platform.controller;

import lombok.RequiredArgsConstructor;
import org.hl7.fhir.dstu3.model.IdType;
import org.librehealth.fhir.platform.model.CObservation;
import org.librehealth.fhir.platform.repository.ObservationRepository;
import org.librehealth.fhir.platform.util.GeneralUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Observation")
@RequiredArgsConstructor
public class ObservationController {

  private final ObservationRepository observationRepository;
  private final ReactiveCassandraOperations reactiveCassandraOperations;

  @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<CObservation>> update(@PathVariable String id, @RequestBody Mono<CObservation> observationMono) {
    Mono<CObservation> updatedObservationMono = observationMono
            .doOnNext(observation -> observation.setObservationId(id));
    return observationRepository
            .findById(id)
            .flatMap(observation -> updatedObservationMono
                    .flatMap(updatedObservation -> {
                      BeanUtils.copyProperties(updatedObservation, observation, GeneralUtils.getInstance().getNullProperties(updatedObservation));
                      return observationRepository.save(updatedObservation);
                    })).map(ResponseEntity::ok)
            .switchIfEmpty(updatedObservationMono
                    .flatMap(observationRepository::save)
                    .map(updatedObservation -> ResponseEntity
                            .created(URI.create(""))
                            .lastModified(ZonedDateTime.now().toEpochSecond())
                            .body(updatedObservation)
                    ));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<CObservation>> save(@RequestBody Mono<CObservation> observationMono) {
    return observationMono
            .doOnNext(observation -> observation.setId(IdType.newRandomUuid()))
            .flatMap(observationRepository::save)
            .map(ResponseEntity::ok);
  }

  @DeleteMapping("{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    return observationRepository
            .deleteById(id)
            .map(i -> ResponseEntity.noContent().build());
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<CObservation>> getById(@PathVariable String id) {
    return observationRepository
            .findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Mono<ResponseEntity<List<CObservation>>> search(@RequestParam Map<String, String> params) {

    if (params.size() == 0) {
      // Return all entities if no search parameters specified
      return observationRepository
              .findAll()
              .collectList()
              .map(ResponseEntity::ok);
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

      return reactiveCassandraOperations
              .select(Query.query(criteriaDefinitions).withAllowFiltering(), CObservation.class)
              .collectList()
              .map(ResponseEntity::ok);
    } catch (IllegalStateException ex) {
      return Mono.just(ResponseEntity.badRequest().build());
    }
  }

  // TODO: 08-Aug-18 Add field specific search criteria support for `Observation`
  private CriteriaDefinition getCriteriaDefinition(String key, String value) {
    throw new UnsupportedOperationException();
  }
}
