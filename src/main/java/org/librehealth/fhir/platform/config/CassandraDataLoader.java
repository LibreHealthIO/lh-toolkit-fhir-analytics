package org.librehealth.fhir.platform.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.IQuery;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.DomainResource;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.librehealth.fhir.analytics.constants.LibreHealthAnalyticConstants;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.utils.LibrehealthAnalyticsUtils;
import org.librehealth.fhir.platform.model.CPatient;
import org.librehealth.fhir.platform.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CassandraDataLoader implements CommandLineRunner {

  private final PatientRepository patientRepository;
  private final FhirContext fhirContext = FhirContext.forDstu3();

  @Value("${fhir.server.url}")
  private String server;

  @Value("${fhir.server.resource.count}")
  private int count;

  @Override
  public void run(String... args) throws Exception {
    Bundle patientBundle;
    IGenericClient client = fhirContext.newRestfulGenericClient(server);
    patientBundle = getData(client, count, Patient.class);
    List<CPatient> patients = patientBundle
            .getEntry()
            .stream()
            .map(entry -> castToCustomPatient((Patient) entry.getResource()))
            .collect(Collectors.toList());
    patientRepository.saveAll(patients).subscribe();

    FhirContext fhirCtx = FhirContext.forDstu3();
    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
    ClassLoader loader = getClass().getClassLoader();
    URL url = loader.getResource(LibreHealthAnalyticConstants.DATA_PATH);
    String path = url.getPath();
    File[] files = new File(path).listFiles();
    String resource, resourceType;
    Resource fhirResource;
    Bundle bundle;
    for (File file : files) {
      try {
        resource = LibrehealthAnalyticsUtils.readFileAsString(file);
        IBaseResource baseResource = parser.parseResource(resource);
        IIdType iIdType = baseResource.getIdElement();
        resourceType = iIdType.getResourceType();
        if (!(baseResource instanceof Bundle) && baseResource instanceof Resource) {
          if (StringUtils.isEmpty(resourceType) || StringUtils.isEmpty(iIdType.getIdPart())) {
            continue;
          }
          if (resourceType.equalsIgnoreCase("patient")) {
            patientRepository.save(castToCustomPatient((Patient) baseResource)).subscribe();
          }
        } else {
          bundle = (Bundle) baseResource;
          for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            fhirResource = entry.getResource();
            resourceType = fhirResource.getResourceType().name();
            if (resourceType.equalsIgnoreCase("patient")) {
              patientRepository.save(castToCustomPatient((Patient) fhirResource)).subscribe();
            }
          }
        }
      } catch (IOException e) {
        throw new LibreHealthFHIRAnalyticsException("Error while reading data from files", e);
      }
    }

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

  public CPatient castToCustomPatient(Patient patient) {
    CPatient customPatient = new CPatient();
    BeanUtils.copyProperties(patient, customPatient);
    return customPatient;
  }
}
