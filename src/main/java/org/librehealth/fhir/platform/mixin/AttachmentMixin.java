package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.DateTimeType;

public interface AttachmentMixin {

  /**
   * @see Attachment#getCreationElement()
   */
  @JsonIgnore
  DateTimeType getCreationElement();
}
