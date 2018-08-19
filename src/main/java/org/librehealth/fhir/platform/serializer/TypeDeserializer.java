package org.librehealth.fhir.platform.serializer;

import ca.uhn.fhir.parser.DataFormatException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.DateType;
import org.hl7.fhir.dstu3.model.IntegerType;
import org.hl7.fhir.dstu3.model.PrimitiveType;
import org.hl7.fhir.dstu3.model.Type;

import java.io.IOException;

public class TypeDeserializer extends StdDeserializer<Type> {

  public TypeDeserializer() {
    super(Type.class);
  }

  @Override
  public Type deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    String data = p.getText();
    // TODO: 07-Jul-18 We need a better way to handle multi data type properties
    // Some properties in HAPI FHIR DSTU3 models have their data type set to org.hl7.fhir.dstu3.model.Type
    // to support multiple data types for e.g Patient#deceased can be BooleanType or DateType.
    // See https://medium.com/@yashdsaraf/gsoc-librehealth-finalizing-crud-for-patient-model-57aa57273360
    PrimitiveType<?>[] types = new PrimitiveType<?>[]{new BooleanType(), new DateType(), new IntegerType()};
    for (PrimitiveType<?> type : types) {
      try {
        type.setValueAsString(data);
        return type;
      } catch (DataFormatException ex) {
        continue;
      }
    }
    throw new DataFormatException("Given data doesn't match any of the known types: " + data);
  }
}
