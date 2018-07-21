package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Patient.AnimalComponent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class AnimalComponentConverter implements Converter<String, AnimalComponent> {

  @Override
  public AnimalComponent convert(String source) {
    AnimalComponent component = ConvertUtils.getInstance().fromString(source, AnimalComponent.class);
    System.out.println();
    return component;
  }
}
