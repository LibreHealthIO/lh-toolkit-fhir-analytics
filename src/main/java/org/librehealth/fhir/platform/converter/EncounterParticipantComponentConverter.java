package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Encounter.EncounterParticipantComponent;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class EncounterParticipantComponentConverter implements Converter<String, EncounterParticipantComponent> {

  @Override
  public EncounterParticipantComponent convert(String source) {
    return ConvertUtils.getInstance().fromString(source, EncounterParticipantComponent.class);
  }
}
