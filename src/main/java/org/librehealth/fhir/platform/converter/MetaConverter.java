package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Meta;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class MetaConverter implements Converter<String, Meta> {

  @Override
  public Meta convert(String source) {
    return ConvertUtils.getInstance().fromString(source, Meta.class);
  }
}
