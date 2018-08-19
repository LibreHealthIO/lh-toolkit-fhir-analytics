package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.HumanName;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@CassandraConverter
@ReadingConverter
public class HumanNameConverter implements Converter<String, HumanName> {

  @Override
  public HumanName convert(String source) {
    return ConvertUtils.getInstance().fromString(source, HumanName.class);
  }
}
