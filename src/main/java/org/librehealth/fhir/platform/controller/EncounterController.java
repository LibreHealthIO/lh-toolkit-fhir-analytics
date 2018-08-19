package org.librehealth.fhir.platform.controller;

import lombok.RequiredArgsConstructor;
import org.hl7.fhir.dstu3.model.IdType;
import org.librehealth.fhir.platform.model.CEncounter;
import org.librehealth.fhir.platform.repository.EncounterRepository;
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
@RequestMapping("Encounter")
@RequiredArgsConstructor
public class EncounterController {

  private final EncounterRepository encounterRepository;
  private final ReactiveCassandraOperations reactiveCassandraOperations;

  @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<CEncounter>> update(@PathVariable String id, @RequestBody Mono<CEncounter> encounterMono) {
    Mono<CEncounter> updatedEncounterMono = encounterMono
            .doOnNext(encounter -> encounter.setEncounterId(id));
    return encounterRepository
            .findById(id)
            .flatMap(encounter -> updatedEncounterMono
                    .flatMap(updatedEncounter -> {
                      BeanUtils.copyProperties(updatedEncounter, encounter, GeneralUtils.getInstance().getNullProperties(updatedEncounter));
                      return encounterRepository.save(updatedEncounter);
                    })).map(ResponseEntity::ok)
            .switchIfEmpty(updatedEncounterMono
                    .flatMap(encounterRepository::save)
                    .map(updatedEncounter -> ResponseEntity
                            .created(URI.create(""))
                            .lastModified(ZonedDateTime.now().toEpochSecond())
                            .body(updatedEncounter)
                    ));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<CEncounter>> save(@RequestBody Mono<CEncounter> encounterMono) {
    return encounterMono
            .doOnNext(encounter -> encounter.setId(IdType.newRandomUuid()))
            .flatMap(encounterRepository::save)
            .map(ResponseEntity::ok);
  }

  @DeleteMapping("{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    return encounterRepository
            .deleteById(id)
            .map(i -> ResponseEntity.noContent().build());
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<CEncounter>> getById(@PathVariable String id) {
    return encounterRepository
            .findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Mono<ResponseEntity<List<CEncounter>>> search(@RequestParam Map<String, String> params) {

    if (params.size() == 0) {
      // Return all entities if no search parameters specified
      return encounterRepository
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
              .select(Query.query(criteriaDefinitions).withAllowFiltering(), CEncounter.class)
              .collectList()
              .map(ResponseEntity::ok);
    } catch (IllegalStateException ex) {
      return Mono.just(ResponseEntity.badRequest().build());
    }
  }

  // TODO: 08-Aug-18 Add field specific search criteria support for `Encounter`
  private CriteriaDefinition getCriteriaDefinition(String key, String value) {
    throw new UnsupportedOperationException();
  }
}
