package org.librehealth.fhir.platform.model;

import ca.uhn.fhir.context.FhirVersionEnum;
import com.datastax.driver.core.DataType.Name;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.exceptions.FHIRException;
import org.librehealth.fhir.platform.annotation.SASIIndex;
import org.librehealth.fhir.platform.annotation.WorkaroundProperty;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table("Patient")
@AccessType(AccessType.Type.PROPERTY)
public class CPatient extends Patient {

  @Transient
  @Override
  public ResourceType getResourceType() {
    return super.getResourceType();
  }

  @Id
  public String getPatientId() {
    return getIdElement().getIdPart();
  }

  public void setPatientId(String value) {
    IdType currentId = getIdElement();
    IdType newId = new IdType(currentId.getBaseUrl(), getResourceType().getPath(), value, currentId.getVersionIdPart());
    setIdElement(newId);
  }

  @Transient
  @Override
  public Meta getMeta() {
    return super.getMeta();
  }

  @WorkaroundProperty("meta")
  public Meta getCMeta() {
    return super.getMeta();
  }

  public void setCMeta(Meta value) {
    super.setMeta(value);
  }

  @Transient
  @CassandraType(type = Name.MAP, typeArguments = {Name.VARCHAR, Name.VARCHAR})
  public Map<String, Object> getUserData() {
    return new HashMap<>();
  }

  @Transient
  @Override
  public boolean isResource() {
    return super.isResource();
  }

  @Transient
  @Override
  public boolean isPrimitive() {
    return super.isPrimitive();
  }

  @Transient
  @Override
  public boolean isEmpty() {
    return super.isEmpty();
  }

  @Transient
  @Override
  public List<String> getFormatCommentsPost() {
    return super.getFormatCommentsPost();
  }

  @Transient
  @Override
  public List<String> getFormatCommentsPre() {
    return super.getFormatCommentsPre();
  }

  @Transient
  @Override
  public String getIdBase() {
    return super.getIdBase();
  }

  @Transient
  @Override
  public IdType getIdElement() {
    return super.getIdElement();
  }

  @Transient
  @Override
  public String getImplicitRules() {
    return super.getImplicitRules();
  }

  @Transient
  @Override
  public UriType getImplicitRulesElement() {
    return super.getImplicitRulesElement();
  }

  @Transient
  @Override
  public CodeType getLanguageElement() {
    return super.getLanguageElement();
  }

  @Transient
  @Override
  public FhirVersionEnum getStructureFhirVersionEnum() {
    return super.getStructureFhirVersionEnum();
  }

  @Transient
  @Override
  public List<Resource> getContained() {
    return super.getContained();
  }

  @WorkaroundProperty("contained")
  public List<Resource> getCContained() {
    return super.getContained();
  }

  public void setCContained(List<Resource> value) {
    super.setContained(value);
  }

  @Transient
  @Override
  public List<Extension> getExtension() {
    return super.getExtension();
  }

  @WorkaroundProperty("extension")
  public List<Extension> getCExtension() {
    return super.getExtension();
  }

  public void setCExtension(List<Extension> value) {
    super.setExtension(value);
  }

  @Transient
  @Override
  public List<Extension> getModifierExtension() {
    return super.getModifierExtension();
  }

  @WorkaroundProperty("modifierExtension")
  public List<Extension> getCModifierExtension() {
    return super.getModifierExtension();
  }

  public void setCModifierExtension(List<Extension> value) {
    super.setModifierExtension(value);
  }

  @Transient
  @Override
  public Narrative getText() {
    return super.getText();
  }

  @WorkaroundProperty("text")
  public Narrative getCText() {
    return super.getText();
  }

  public void setCText(Narrative value) {
    super.setText(value);
  }

  @Transient
  @Override
  public List<Address> getAddress() {
    return super.getAddress();
  }

  @WorkaroundProperty("address")
  public List<Address> getCAddress() {
    return super.getAddress();
  }

  public void setCAddress(List<Address> value) {
    super.setAddress(value);
  }

  @Transient
  @Override
  public Address getAddressFirstRep() {
    return super.getAddressFirstRep();
  }

  @Transient
  @Override
  public AnimalComponent getAnimal() {
    return super.getAnimal();
  }

  @SASIIndex
  @WorkaroundProperty("animal")
  public AnimalComponent getCAnimal() {
    return super.getAnimal();
  }

  public void setCAnimal(AnimalComponent animal) {
    super.setAnimal(animal);
  }

  @Transient
  @Override
  public List<PatientCommunicationComponent> getCommunication() {
    return super.getCommunication();
  }

  @WorkaroundProperty("communication")
  public List<PatientCommunicationComponent> getCCommunication() {
    return super.getCommunication();
  }

  public void setCCommunication(List<PatientCommunicationComponent> value) {
    super.setCommunication(value);
  }

  @Transient
  @Override
  public PatientCommunicationComponent getCommunicationFirstRep() {
    return super.getCommunicationFirstRep();
  }

  @Transient
  @Override
  public List<ContactComponent> getContact() {
    return super.getContact();
  }

  @WorkaroundProperty("contact")
  public List<ContactComponent> getCContact() {
    return super.getContact();
  }

  public void setCContact(List<ContactComponent> value) {
    super.setContact(value);
  }

  @Transient
  @Override
  public ContactComponent getContactFirstRep() {
    return super.getContactFirstRep();
  }

  @Transient
  @Override
  public Type getDeceased() {
    return super.getDeceased();
  }

  @SASIIndex
  @WorkaroundProperty("deceased")
  public Type getCDeceased() {
    return super.getDeceased();
  }

  public void setCDeceased(Type value) {
    super.setDeceased(value);
  }

  @Transient
  @Override
  public List<Reference> getGeneralPractitioner() {
    return super.getGeneralPractitioner();
  }

  @WorkaroundProperty("generalPractitioner")
  public List<Reference> getCGeneralPractitioner() {
    return super.getGeneralPractitioner();
  }

  public void setCGeneralPractitioner(List<Reference> value) {
    super.setGeneralPractitioner(value);
  }

  @Transient
  @Override
  public Reference getGeneralPractitionerFirstRep() {
    return super.getGeneralPractitionerFirstRep();
  }

  @Transient
  @Override
  public List<Resource> getGeneralPractitionerTarget() {
    return super.getGeneralPractitionerTarget();
  }

  @Transient
  @Override
  public List<Identifier> getIdentifier() {
    return super.getIdentifier();
  }

  @WorkaroundProperty("identifier")
  public List<Identifier> getCIdentifier() {
    return super.getIdentifier();
  }

  public void setCIdentifier(List<Identifier> value) {
    super.setIdentifier(value);
  }

  @Transient
  @Override
  public Identifier getIdentifierFirstRep() {
    return super.getIdentifierFirstRep();
  }

  @Transient
  @Override
  public List<PatientLinkComponent> getLink() {
    return super.getLink();
  }

  @WorkaroundProperty("link")
  public List<PatientLinkComponent> getCLink() {
    return super.getLink();
  }

  public void setCLink(List<PatientLinkComponent> value) {
    super.setLink(value);
  }

  @Transient
  @Override
  public PatientLinkComponent getLinkFirstRep() {
    return super.getLinkFirstRep();
  }

  @Transient
  @Override
  public Organization getManagingOrganizationTarget() {
    return super.getManagingOrganizationTarget();
  }

  @Transient
  @Override
  public Reference getManagingOrganization() {
    return super.getManagingOrganization();
  }

  @SASIIndex
  @WorkaroundProperty("managingOrganization")
  public Reference getCManagingOrganization() {
    return super.getManagingOrganization();
  }

  public void setCManagingOrganization(Reference value) {
    super.setManagingOrganization(value);
  }

  @Transient
  @Override
  public CodeableConcept getMaritalStatus() {
    return super.getMaritalStatus();
  }

  @WorkaroundProperty("maritalStatus")
  public CodeableConcept getCMaritalStatus() {
    return super.getMaritalStatus();
  }

  public void setCMaritalStatus(CodeableConcept value) {
    super.setMaritalStatus(value);
  }

  @Transient
  @Override
  public Type getMultipleBirth() {
    return super.getMultipleBirth();
  }

  @SASIIndex
  @WorkaroundProperty("multipleBirth")
  public Type getCMultipleBirth() {
    return super.getMultipleBirth();
  }

  public void setCMultipleBirth(Type value) {
    super.setMultipleBirth(value);
  }

  @Transient
  @Override
  public List<HumanName> getName() {
    return super.getName();
  }

  @WorkaroundProperty("name")
  public List<HumanName> getCName() {
    return super.getName();
  }

  public void setCName(List<HumanName> value) {
    super.setName(value);
  }

  @Transient
  @Override
  public HumanName getNameFirstRep() {
    return super.getNameFirstRep();
  }

  @Transient
  @Override
  public List<Attachment> getPhoto() {
    return super.getPhoto();
  }

  @WorkaroundProperty("photo")
  public List<Attachment> getCPhoto() {
    return super.getPhoto();
  }

  public void setCPhoto(List<Attachment> value) {
    super.setPhoto(value);
  }

  @Transient
  @Override
  public Attachment getPhotoFirstRep() {
    return super.getPhotoFirstRep();
  }

  @Transient
  @Override
  public List<ContactPoint> getTelecom() {
    return super.getTelecom();
  }

  @WorkaroundProperty("telecom")
  public List<ContactPoint> getCTelecom() {
    return super.getTelecom();
  }

  public void setCTelecom(List<ContactPoint> value) {
    super.setTelecom(value);
  }

  @Transient
  @Override
  public ContactPoint getTelecomFirstRep() {
    return super.getTelecomFirstRep();
  }

  @Transient
  @Override
  public BooleanType getDeceasedBooleanType() throws FHIRException {
    return super.getDeceasedBooleanType();
  }

  @Transient
  @Override
  public DateTimeType getDeceasedDateTimeType() throws FHIRException {
    return super.getDeceasedDateTimeType();
  }

  @Transient
  @Override
  public BooleanType getMultipleBirthBooleanType() throws FHIRException {
    return super.getMultipleBirthBooleanType();
  }

  @Transient
  @Override
  public IntegerType getMultipleBirthIntegerType() throws FHIRException {
    return super.getMultipleBirthIntegerType();
  }

  @Override
  public AdministrativeGender getGender() {
    return super.getGender();
  }

  @Transient
  @Override
  public Enumeration<AdministrativeGender> getGenderElement() {
    return super.getGenderElement();
  }

  @Transient
  @Override
  public BooleanType getActiveElement() {
    return super.getActiveElement();
  }

  @Transient
  @Override
  public DateType getBirthDateElement() {
    return super.getBirthDateElement();
  }
}
