package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

import java.util.List;

public interface XhtmlNodeMixin {

  /**
   * @see XhtmlNode#getFormatCommentsPost()
   */
  @JsonIgnore
  List<String> getFormatCommentsPost();

  /**
   * @see XhtmlNode#getFormatCommentsPre()
   */
  @JsonIgnore
  List<String> getFormatCommentsPre();
}