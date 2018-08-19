package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Resource;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@CassandraConverter
@WritingConverter
public class ResourceConverter implements Converter<Resource, String> {

  @Override
  public String convert(Resource source) {
    return ConvertUtils.getInstance().toString(source);
  }
}
