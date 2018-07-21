package org.librehealth.fhir.platform.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.hl7.fhir.utilities.xhtml.NodeType;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

import java.io.IOException;

public class XhtmlNodeDeserializer extends StdDeserializer<XhtmlNode> {

  public XhtmlNodeDeserializer() {
    super(XhtmlNode.class);
  }

  @Override
  public XhtmlNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    XhtmlNode node = new XhtmlNode(NodeType.Element, "div");
    return node.setValue(p.getText());
  }
}
