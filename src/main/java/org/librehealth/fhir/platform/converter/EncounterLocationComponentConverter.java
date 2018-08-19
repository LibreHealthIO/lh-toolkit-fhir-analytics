package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Encounter.EncounterLocationComponent;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class EncounterLocationComponentConverter implements Converter<String, EncounterLocationComponent> {

  @Override
  public EncounterLocationComponent convert(String source) {
    return ConvertUtils.getInstance().fromString(source, EncounterLocationComponent.class);
  }
}
