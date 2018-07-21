package org.librehealth.fhir.platform.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

import java.io.IOException;

public class XhtmlNodeSerializer extends StdSerializer<XhtmlNode> {

  public XhtmlNodeSerializer() {
    super(XhtmlNode.class);
  }

  @Override
  public void serialize(XhtmlNode value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(value.getValueAsString());
  }
}
