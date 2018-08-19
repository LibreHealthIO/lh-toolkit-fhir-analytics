package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.BaseExtension;
import org.hl7.fhir.instance.model.api.IPrimitiveType;

public interface BaseExtensionMixin {

  /**
   * @see BaseExtension#getValueAsPrimitive()
   */
  @JsonIgnore
  IPrimitiveType<?> getValueAsPrimitive();
}
