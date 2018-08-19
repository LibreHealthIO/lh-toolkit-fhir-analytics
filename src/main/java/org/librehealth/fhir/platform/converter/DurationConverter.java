package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Duration;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class DurationConverter implements Converter<String, Duration> {

  @Override
  public Duration convert(String source) {
    return ConvertUtils.getInstance().fromString(source, Duration.class);
  }
}
