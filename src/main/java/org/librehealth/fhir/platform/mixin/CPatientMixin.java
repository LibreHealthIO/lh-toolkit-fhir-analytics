package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.DateType;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Enumerations;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Type;
import org.librehealth.fhir.platform.model.CPatient;

import java.util.List;

/**
 * @author Yash D. Saraf <yashdsaraf@gmail.com>
 */
@JsonPropertyOrder(alphabetic = true)
public interface CPatientMixin {

  /**
   * @see CPatient#getDeceased()
   */
  @JsonIgnore
  Type getDeceased();

  /**
   * @see CPatient#getDeceasedBooleanType()
   */
  @JsonIgnore
  BooleanType getDeceasedBooleanType();

  /**
   * @see CPatient#getDeceasedDateTimeType()
   */
  @JsonIgnore
  DateTimeType getDeceasedDateTimeType();

  /**
   * @see CPatient#getMultipleBirth()
   */
  @JsonIgnore
  Type getMultipleBirth();

  /**
   * @see CPatient#getMultipleBirthBooleanType()
   */
  @JsonIgnore
  BooleanType getMultipleBirthBooleanType();

  /**
   * @see CPatient#getMultipleBirthIntegerType()
   */
  @JsonIgnore
  DateTimeType getMultipleBirthIntegerType();

  /**
   * @see CPatient#getManagingOrganizationTarget()
   */
  @JsonIgnore
  Organization getManagingOrganizationTarget();

  /**
   * @see CPatient#getGeneralPractitionerTarget()
   */
  @JsonIgnore
  List<Resource> getGeneralPractitionerTarget();

  /**
   * @see CPatient#getTelecomFirstRep()
   */
  @JsonIgnore
  ContactPoint getTelecomFirstRep();

  /**
   * @see CPatient#getIdentifierFirstRep()
   */
  @JsonIgnore
  Identifier getIdentifierFirstRep();

  /**
   * @see CPatient#getNameFirstRep()
   */
  @JsonIgnore
  HumanName getNameFirstRep();

  /**
   * @see CPatient#getAddressFirstRep()
   */
  @JsonIgnore
  Address getAddressFirstRep();

  /**
   * @see CPatient#getPhotoFirstRep()
   */
  @JsonIgnore
  Attachment getPhotoFirstRep();

  /**
   * @see CPatient#getContactFirstRep()
   */
  @JsonIgnore
  Patient.ContactComponent getContactFirstRep();

  /**
   * @see CPatient#getCommunicationFirstRep()
   */
  @JsonIgnore
  Patient.PatientCommunicationComponent getCommunicationFirstRep();

  /**
   * @see CPatient#getGeneralPractitionerFirstRep()
   */
  @JsonIgnore
  Reference getGeneralPractitionerFirstRep();

  /**
   * @see CPatient#getLinkFirstRep()
   */
  @JsonIgnore
  Patient.PatientLinkComponent getLinkFirstRep();

  /**
   * @see CPatient#getActiveElement()
   */
  @JsonIgnore
  BooleanType getActiveElement();

  /**
   * @see CPatient#getBirthDateElement()
   */
  @JsonIgnore
  DateType getBirthDateElement();

  /**
   * @see CPatient#getGenderElement()
   */
  @JsonIgnore
  Enumeration<Enumerations.AdministrativeGender> getGenderElement();

  /**
   * @see CPatient#getPatientId()
   */
  @JsonProperty("id")
  String getPatientId();
}
