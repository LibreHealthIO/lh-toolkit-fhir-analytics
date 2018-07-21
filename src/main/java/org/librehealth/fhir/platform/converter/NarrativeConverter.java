package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Narrative;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class NarrativeConverter implements Converter<String, Narrative> {

  @Override
  public Narrative convert(String source) {
    return ConvertUtils.getInstance().fromString(source, Narrative.class);
  }
}
