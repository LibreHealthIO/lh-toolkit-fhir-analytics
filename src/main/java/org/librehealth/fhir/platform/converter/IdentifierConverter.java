package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Identifier;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@CassandraConverter
@ReadingConverter
public class IdentifierConverter implements Converter<String, Identifier> {

  @Override
  public Identifier convert(String source) {
    return ConvertUtils.getInstance().fromString(source, Identifier.class);
  }
}
