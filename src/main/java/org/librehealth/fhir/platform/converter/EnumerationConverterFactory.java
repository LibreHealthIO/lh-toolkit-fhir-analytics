package org.librehealth.fhir.platform.converter;

import lombok.RequiredArgsConstructor;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EnumFactory;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Enumerations;
import org.hl7.fhir.dstu3.model.Observation;
import org.librehealth.fhir.platform.annotation.CassandraConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.data.convert.ReadingConverter;

@CassandraConverter
@ReadingConverter
public class EnumerationConverterFactory implements ConverterFactory<String, Enumeration> {

  @SuppressWarnings("NullableProblems")
  @Override
  public <T extends Enumeration> Converter<String, T> getConverter(Class<T> targetType) {
    //noinspection unchecked
    return new StringToEnumerationConverter(targetType);
  }

  @RequiredArgsConstructor
  private static class StringToEnumerationConverter<T extends Enumeration> implements Converter<String, T> {

    private final Class<T> enumType;

    @Override
    public T convert(String source) {
      Enumeration<?> enumeration = null;
      // FIXME: 06-Aug-18 We need a better way to determine which enumfactory to use for the given enumeration type
      EnumFactory<?>[] factories = new EnumFactory[] {
              new Observation.ObservationStatusEnumFactory(),
              new Enumerations.AdministrativeGenderEnumFactory(),
              new Encounter.EncounterStatusEnumFactory()
      };
      for (EnumFactory<?> factory: factories) {
        try {
          factory.fromCode(source);
          enumeration = new Enumeration<>(factory);
        } catch (IllegalArgumentException ignored) {
        }
      }
      if (enumeration == null) {
        throw new IllegalStateException("No appropriate EnumFactory found for value: " + source);
      }
      enumeration.setValueAsString(source);
      return (T) enumeration;
    }
  }
}
