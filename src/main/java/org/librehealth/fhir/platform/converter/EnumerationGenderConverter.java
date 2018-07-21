package org.librehealth.fhir.platform.converter;

import org.hl7.fhir.dstu3.model.Enumeration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import static org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGenderEnumFactory;

public class EnumerationGenderConverter {
  @Component
  public static class EnumerationGenderWriteConverter implements Converter<Enumeration<AdministrativeGender>, String> {

    @Override
    public String convert(Enumeration<AdministrativeGender> genderEnumeration) {
      return genderEnumeration.getValueAsString();
    }

  }

  @Component
  public static class EnumerationGenderReadConverter implements Converter<String, Enumeration<AdministrativeGender>> {

    @Override
    public Enumeration<AdministrativeGender> convert(String s) {
      Enumeration<AdministrativeGender> genderEnumeration = new Enumeration<>(new AdministrativeGenderEnumFactory());
      genderEnumeration.setValueAsString(s);
      return genderEnumeration;
    }
  }

}
