package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Encounter.DiagnosisComponent;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class DiagnosisComponentConverter implements Converter<String, DiagnosisComponent> {

  @Override
  public DiagnosisComponent convert(String source) {
    return ConvertUtils.getInstance().fromString(source, DiagnosisComponent.class);
  }
}
