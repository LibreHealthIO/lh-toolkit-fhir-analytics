package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class CodeableConceptConverter implements Converter<String, CodeableConcept> {

  @Override
  public CodeableConcept convert(String source) {
    return ConvertUtils.getInstance().fromString(source, CodeableConcept.class);
  }
}
