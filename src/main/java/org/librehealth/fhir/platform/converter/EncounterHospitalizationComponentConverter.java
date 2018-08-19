package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Encounter.EncounterHospitalizationComponent;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class EncounterHospitalizationComponentConverter implements Converter<String, EncounterHospitalizationComponent> {

  @Override
  public EncounterHospitalizationComponent convert(String source) {
    return ConvertUtils.getInstance().fromString(source, EncounterHospitalizationComponent.class);
  }
}
