package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.IdType;

import java.math.BigDecimal;

public interface IdTypeMixin {

  /**
   * @see IdType#getIdPartAsBigDecimal()
   */
  @JsonIgnore
  BigDecimal getIdPartAsBigDecimal();

  /**
   * @see IdType#getIdPartAsLong()
   */
  @JsonIgnore
  Long getIdPartAsLong();

  /**
   * @see IdType#getVersionIdPartAsLong()
   */
  @JsonIgnore
  Long getVersionIdPartAsLong();
}