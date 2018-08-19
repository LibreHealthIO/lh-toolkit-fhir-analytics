package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Ratio;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class RatioConverter implements Converter<String, Ratio> {

  @Override
  public Ratio convert(String source) {
    return ConvertUtils.getInstance().fromString(source, Ratio.class);
  }
}
