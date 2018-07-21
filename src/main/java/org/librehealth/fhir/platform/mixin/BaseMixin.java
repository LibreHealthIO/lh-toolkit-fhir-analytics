package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Base;

import java.util.List;
import java.util.Map;

public interface BaseMixin {
  /**
   * @see Base#getFormatCommentsPost()
   */
  @JsonIgnore
  List<String> getFormatCommentsPost();

  /**
   * @see Base#getFormatCommentsPre()
   */
  @JsonIgnore
  List<String> getFormatCommentsPre();

  /**
   * @see Base#isEmpty()
   */
  @JsonIgnore
  boolean isEmpty();

  /**
   * @see Base#isBooleanPrimitive()
   */
  @JsonIgnore
  boolean isBooleanPrimitive();

  /**
   * @see Base#isPrimitive()
   */
  @JsonIgnore
  boolean isPrimitive();

  /**
   * @see Base#isResource()
   */
  @JsonIgnore
  boolean isResource();

  /**
   * @see Base#userData
   */
  @JsonIgnore
  Map<String, Object> getUserData();

  /**
   * @see Base#getIdBase()
   */
  @JsonIgnore
  String getIdBase();
}
