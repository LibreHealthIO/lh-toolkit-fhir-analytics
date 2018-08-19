package org.librehealth.fhir.platform.converter;

import ca.uhn.fhir.parser.DataFormatException;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.DateType;
import org.hl7.fhir.dstu3.model.IntegerType;
import org.hl7.fhir.dstu3.model.PrimitiveType;
import org.hl7.fhir.dstu3.model.Type;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;


public class TypeConverter {

  @Component
  @CassandraConverter
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
  @CassandraConverter
  @ReadingConverter
  public static class TypeReadConverter implements Converter<String, Type> {

    @Override
    public Type convert(String source) {
      PrimitiveType<?>[] types = new PrimitiveType<?>[]{new BooleanType(), new DateTimeType(), new DateType(), new IntegerType()};
      for (PrimitiveType<?> type : types) {
        try {
          type.setValueAsString(source);
          return type;
        } catch (DataFormatException ex) {
          continue;
        }
      }
      throw new DataFormatException("Given data doesn't match any of the known types: " + source);
    }
  }
}
