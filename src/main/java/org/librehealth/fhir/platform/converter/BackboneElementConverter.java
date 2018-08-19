package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.BackboneElement;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@CassandraConverter
@WritingConverter
public class BackboneElementConverter implements Converter<BackboneElement, String> {

  @Override
  public String convert(BackboneElement source) {
    return ConvertUtils.getInstance().toString(source);
  }
}
