package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.InstantType;
import org.hl7.fhir.dstu3.model.Observation.ObservationComponentComponent;
import org.hl7.fhir.dstu3.model.Observation.ObservationReferenceRangeComponent;
import org.hl7.fhir.dstu3.model.Observation.ObservationRelatedComponent;
import org.hl7.fhir.dstu3.model.Observation.ObservationStatus;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Specimen;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.TimeType;
import org.hl7.fhir.dstu3.model.Type;
import org.librehealth.fhir.platform.model.CObservation;

import java.util.List;

@JsonPropertyOrder(alphabetic = true)
public interface CObservationMixin {

  /**
   * @see CObservation#getObservationId()
   */
  @JsonProperty("id")
  String getObservationId();

  /**
   * @see CObservation#getSpecimenTarget()
   */
  @JsonIgnore
  Specimen getSpecimenTarget();

  /**
   * @see CObservation#getIssuedElement()
   */
  @JsonIgnore
  InstantType getIssuedElement();

  /**
   * @see CObservation#getReferenceRangeFirstRep()
   */
  @JsonIgnore
  ObservationReferenceRangeComponent getReferenceRangeFirstRep();

  /**
   * @see CObservation#getComponentFirstRep()
   */
  @JsonIgnore
  ObservationComponentComponent getComponentFirstRep();

  /**
   * @see CObservation#getIdentifierFirstRep()
   */
  @JsonIgnore
  Identifier getIdentifierFirstRep();

  /**
   * @see CObservation#getStatusElement()
   */
  @JsonIgnore
  Enumeration<ObservationStatus> getStatusElement();

  /**
   * @see CObservation#getRelatedFirstRep()
   */
  @JsonIgnore
  ObservationRelatedComponent getRelatedFirstRep();

  /**
   * @see CObservation#getCommentElement()
   */
  @JsonIgnore
  StringType getCommentElement();

  /**
   * @see CObservation#getBasedOnFirstRep()
   */
  @JsonIgnore
  Reference getBasedOnFirstRep();

  /**
   * @see CObservation#getPerformerFirstRep()
   */
  @JsonIgnore
  Reference getPerformerFirstRep();

  /**
   * @see CObservation#getCategoryFirstRep()
   */
  @JsonIgnore
  CodeableConcept getCategoryFirstRep();

  /**
   * @see CObservation#getBasedOnTarget()
   */
  @JsonIgnore
  List<Resource> getBasedOnTarget();

  /**
   * @see CObservation#getContextTarget()
   */
  @JsonIgnore
  Resource getContextTarget();

  /**
   * @see CObservation#getDeviceTarget()
   */
  @JsonIgnore
  Resource getDeviceTarget();

  /**
   * @see CObservation#getEffective()
   */
  @JsonIgnore
  Type getEffective();

  /**
   * @see CObservation#getEffectiveDateTimeType()
   */
  @JsonProperty("effectiveDateTime")
  DateTimeType getEffectiveDateTimeType();

  /**
   * @see CObservation#getPerformerTarget()
   */
  @JsonIgnore
  List<Resource> getPerformerTarget();

  /**
   * @see CObservation#getSubjectTarget()
   */
  @JsonIgnore
  Resource getSubjectTarget();

  /**
   * @see CObservation#getValue()
   */
  @JsonIgnore
  Type getValue();

  /**
   * @see CObservation#getValueBooleanType()
   */
  @JsonProperty("valueBoolean")
  BooleanType getValueBooleanType();

  /**
   * @see CObservation#getValueDateTimeType()
   */
  @JsonProperty("valueDateTime")
  DateTimeType getValueDateTimeType();

  /**
   * @see CObservation#getValueStringType()
   */
  @JsonProperty("valueString")
  StringType getValueStringType();

  /**
   * @see CObservation#getValueTimeType()
   */
  @JsonProperty("valueTime")
  TimeType getValueTimeType();

}
