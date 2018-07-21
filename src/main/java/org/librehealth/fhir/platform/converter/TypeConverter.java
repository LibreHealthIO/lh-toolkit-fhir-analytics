package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Type;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;


public class TypeConverter {

  @Component
  @WritingConverter
  public static class TypeWriteConverter implements Converter<Type, String> {

    @Override
    public String convert(Type source) {
      if (source.isPrimitive()) {
        return source.primitiveValue();
      }
      return ConvertUtils.getInstance().toString(source);
    }
  }

  @Component
  @ReadingConverter
  public static class TypeReadConverter implements Converter<String, Type> {

    @Override
    public Type convert(String source) {
      return ConvertUtils.getInstance().fromString(source, Type.class);
    }
  }
}
