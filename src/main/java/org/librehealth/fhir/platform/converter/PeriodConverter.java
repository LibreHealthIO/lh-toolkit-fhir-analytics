package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Period;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class PeriodConverter implements Converter<String, Period> {

  @Override
  public Period convert(String source) {
    return ConvertUtils.getInstance().fromString(source, Period.class);
  }
}
