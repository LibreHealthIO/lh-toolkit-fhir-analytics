package org.librehealth.fhir.platform.model;

import ca.uhn.fhir.context.FhirVersionEnum;
import org.hl7.fhir.dstu3.model.Account;
import org.hl7.fhir.dstu3.model.Appointment;
import org.hl7.fhir.dstu3.model.Base;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.Duration;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.Extension;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Meta;
import org.hl7.fhir.dstu3.model.Narrative;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Property;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.ResourceType;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.exceptions.FHIRException;
import org.librehealth.fhir.platform.annotation.WorkaroundProperty;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table("Encounter")
@AccessType(AccessType.Type.PROPERTY)
public class CEncounter extends Encounter {

  @Id
  public String getEncounterId() {
    return getIdElement().getIdPart();
  }

  public void setEncounterId(String value) {
    IdType currentId = getIdElement();
    IdType newId = new IdType(currentId.getBaseUrl(), getResourceType().getPath(), value, currentId.getVersionIdPart());
    setIdElement(newId);
  }

  @Override
  @Transient
  public List<Identifier> getIdentifier() {
    return super.getIdentifier();
  }

  @Override
  @Transient
  public Identifier getIdentifierFirstRep() {
    return super.getIdentifierFirstRep();
  }

  @Override
  @Transient
  public Enumeration<EncounterStatus> getStatusElement() {
    return super.getStatusElement();
  }

  @Override
  @Transient
  public List<StatusHistoryComponent> getStatusHistory() {
    return super.getStatusHistory();
  }

  @Override
  @Transient
  public StatusHistoryComponent getStatusHistoryFirstRep() {
    return super.getStatusHistoryFirstRep();
  }

  @Override
  @Transient
  public Coding getClass_() {
    return super.getClass_();
  }

  @Override
  @Transient
  public List<ClassHistoryComponent> getClassHistory() {
    return super.getClassHistory();
  }

  @Override
  @Transient
  public ClassHistoryComponent getClassHistoryFirstRep() {
    return super.getClassHistoryFirstRep();
  }

  @Override
  @Transient
  public List<CodeableConcept> getType() {
    return super.getType();
  }

  @Override
  @Transient
  public CodeableConcept getTypeFirstRep() {
    return super.getTypeFirstRep();
  }

  @Override
  @Transient
  public CodeableConcept getPriority() {
    return super.getPriority();
  }

  @Override
  @Transient
  public Reference getSubject() {
    return super.getSubject();
  }

  @Override
  @Transient
  public Resource getSubjectTarget() {
    return super.getSubjectTarget();
  }

  @Override
  @Transient
  public List<Reference> getEpisodeOfCare() {
    return super.getEpisodeOfCare();
  }

  @Override
  @Transient
  public Reference getEpisodeOfCareFirstRep() {
    return super.getEpisodeOfCareFirstRep();
  }

  @Override
  @Transient
  public List<EpisodeOfCare> getEpisodeOfCareTarget() {
    return super.getEpisodeOfCareTarget();
  }

  @Override
  @Transient
  public List<Reference> getIncomingReferral() {
    return super.getIncomingReferral();
  }

  @Override
  @Transient
  public Reference getIncomingReferralFirstRep() {
    return super.getIncomingReferralFirstRep();
  }

  @Override
  @Transient
  public List<ReferralRequest> getIncomingReferralTarget() {
    return super.getIncomingReferralTarget();
  }

  @Override
  @Transient
  public List<EncounterParticipantComponent> getParticipant() {
    return super.getParticipant();
  }

  @Override
  @Transient
  public EncounterParticipantComponent getParticipantFirstRep() {
    return super.getParticipantFirstRep();
  }

  @Override
  @Transient
  public Reference getAppointment() {
    return super.getAppointment();
  }

  @Override
  @Transient
  public Appointment getAppointmentTarget() {
    return super.getAppointmentTarget();
  }

  @Override
  @Transient
  public Period getPeriod() {
    return super.getPeriod();
  }

  @Override
  @Transient
  public Duration getLength() {
    return super.getLength();
  }

  @Override
  @Transient
  public List<CodeableConcept> getReason() {
    return super.getReason();
  }

  @Override
  @Transient
  public CodeableConcept getReasonFirstRep() {
    return super.getReasonFirstRep();
  }

  @Override
  @Transient
  public List<DiagnosisComponent> getDiagnosis() {
    return super.getDiagnosis();
  }

  @Override
  @Transient
  public DiagnosisComponent getDiagnosisFirstRep() {
    return super.getDiagnosisFirstRep();
  }

  @Override
  @Transient
  public List<Reference> getAccount() {
    return super.getAccount();
  }

  @Override
  @Transient
  public Reference getAccountFirstRep() {
    return super.getAccountFirstRep();
  }

  @Override
  @Transient
  public List<Account> getAccountTarget() {
    return super.getAccountTarget();
  }

  @Override
  @Transient
  public EncounterHospitalizationComponent getHospitalization() {
    return super.getHospitalization();
  }

  @Override
  @Transient
  public List<EncounterLocationComponent> getLocation() {
    return super.getLocation();
  }

  @Override
  @Transient
  public EncounterLocationComponent getLocationFirstRep() {
    return super.getLocationFirstRep();
  }

  @Override
  @Transient
  public Reference getServiceProvider() {
    return super.getServiceProvider();
  }

  @Override
  @Transient
  public Organization getServiceProviderTarget() {
    return super.getServiceProviderTarget();
  }

  @Override
  @Transient
  public Reference getPartOf() {
    return super.getPartOf();
  }

  @Override
  @Transient
  public Encounter getPartOfTarget() {
    return super.getPartOfTarget();
  }

  @Override
  @Transient
  public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
    return super.getProperty(hash, name, checkValid);
  }

  @Override
  @Transient
  public String[] getTypesForProperty(int hash, String name) throws FHIRException {
    return super.getTypesForProperty(hash, name);
  }

  @Override
  @Transient
  public ResourceType getResourceType() {
    return super.getResourceType();
  }

  @Override
  @Transient
  public Narrative getText() {
    return super.getText();
  }

  @Override
  @Transient
  public List<Resource> getContained() {
    return super.getContained();
  }

  @Override
  @Transient
  public List<Extension> getExtension() {
    return super.getExtension();
  }

  @Override
  @Transient
  public List<Extension> getModifierExtension() {
    return super.getModifierExtension();
  }

  @Override
  @Transient
  public List<Extension> getExtensionsByUrl(String theUrl) {
    return super.getExtensionsByUrl(theUrl);
  }

  @Override
  @Transient
  public List<Extension> getModifierExtensionsByUrl(String theUrl) {
    return super.getModifierExtensionsByUrl(theUrl);
  }

  @Override
  @Transient
  public IdType getIdElement() {
    return super.getIdElement();
  }

  @Override
  @Transient
  public String getId() {
    return super.getId();
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

  @Override
  @Transient
  public UriType getImplicitRulesElement() {
    return super.getImplicitRulesElement();
  }

  @Override
  @Transient
  public String getImplicitRules() {
    return super.getImplicitRules();
  }

  @Override
  @Transient
  public CodeType getLanguageElement() {
    return super.getLanguageElement();
  }

  @Override
  @Transient
  public String getLanguage() {
    return super.getLanguage();
  }

  @Override
  @Transient
  public String getIdBase() {
    return super.getIdBase();
  }

  @Override
  @Transient
  public FhirVersionEnum getStructureFhirVersionEnum() {
    return super.getStructureFhirVersionEnum();
  }

  @Transient
  public Map<String, Object> getUserData() {
    return new HashMap<>();
  }

  @Override
  @Transient
  public String getUserString(String name) {
    return super.getUserString(name);
  }

  @Override
  @Transient
  public int getUserInt(String name) {
    return super.getUserInt(name);
  }

  @Override
  @Transient
  public List<String> getFormatCommentsPre() {
    return super.getFormatCommentsPre();
  }

  @Override
  @Transient
  public List<String> getFormatCommentsPost() {
    return super.getFormatCommentsPost();
  }

  @Override
  @Transient
  public Property getChildByName(String name) {
    return super.getChildByName(name);
  }

  @Override
  @Transient
  public Property getNamedProperty(String _name) throws FHIRException {
    return super.getNamedProperty(_name);
  }

  @Override
  @Transient
  public boolean isEmpty() {
    return super.isEmpty();
  }

  @Override
  @Transient
  public boolean isResource() {
    return super.isResource();
  }

  @Override
  @Transient
  public boolean isPrimitive() {
    return super.isPrimitive();
  }

  @WorkaroundProperty("identifier")
  public List<Identifier> getCIdentifier() {
    return super.getIdentifier();
  }

  public void setCIdentifier(List<Identifier> value) {
    super.setIdentifier(value);
  }

  @WorkaroundProperty("statushistory")
  public List<StatusHistoryComponent> getCStatusHistory() {
    return super.getStatusHistory();
  }

  public void setCStatusHistory(List<StatusHistoryComponent> value) {
    super.setStatusHistory(value);
  }

//  @WorkaroundProperty("class_")
//  public Coding getCClass_() {
//    return super.getClass_();
//  }
//
//  public void setCClass_(Coding value) {
//    super.setClass_(value);
//  }

  @WorkaroundProperty("classhistory")
  public List<ClassHistoryComponent> getCClassHistory() {
    return super.getClassHistory();
  }

  public void setCClassHistory(List<ClassHistoryComponent> value) {
    super.setClassHistory(value);
  }

  @WorkaroundProperty("type")
  public List<CodeableConcept> getCType() {
    return super.getType();
  }

  public void setCType(List<CodeableConcept> value) {
    super.setType(value);
  }

  @WorkaroundProperty("priority")
  public CodeableConcept getCPriority() {
    return super.getPriority();
  }

  public void setCPriority(CodeableConcept value) {
    super.setPriority(value);
  }

  @WorkaroundProperty("subject")
  public Reference getCSubject() {
    return super.getSubject();
  }

  public void setCSubject(Reference value) {
    super.setSubject(value);
  }

  @WorkaroundProperty("episodeofcare")
  public List<Reference> getCEpisodeOfCare() {
    return super.getEpisodeOfCare();
  }

  public void setCEpisodeOfCare(List<Reference> value) {
    super.setEpisodeOfCare(value);
  }

  @WorkaroundProperty("incomingreferral")
  public List<Reference> getCIncomingReferral() {
    return super.getIncomingReferral();
  }

  public void setCIncomingReferral(List<Reference> value) {
    super.setIncomingReferral(value);
  }

  @WorkaroundProperty("participant")
  public List<EncounterParticipantComponent> getCParticipant() {
    return super.getParticipant();
  }

  public void setCParticipant(List<EncounterParticipantComponent> value) {
    super.setParticipant(value);
  }

  @WorkaroundProperty("appointment")
  public Reference getCAppointment() {
    return super.getAppointment();
  }

  public void setCAppointment(Reference value) {
    super.setAppointment(value);
  }

  @WorkaroundProperty("period")
  public Period getCPeriod() {
    return super.getPeriod();
  }

  public void setCPeriod(Period value) {
    super.setPeriod(value);
  }

//  @WorkaroundProperty("length")
//  public Duration getCLength() {
//    return super.getLength();
//  }
//
//  public void setCLength(Duration value) {
//    super.setLength(value);
//  }

  @WorkaroundProperty("reason")
  public List<CodeableConcept> getCReason() {
    return super.getReason();
  }

  public void setCReason(List<CodeableConcept> value) {
    super.setReason(value);
  }

  @WorkaroundProperty("diagnosis")
  public List<DiagnosisComponent> getCDiagnosis() {
    return super.getDiagnosis();
  }

  public void setCDiagnosis(List<DiagnosisComponent> value) {
    super.setDiagnosis(value);
  }

  @WorkaroundProperty("account")
  public List<Reference> getCAccount() {
    return super.getAccount();
  }

  public void setCAccount(List<Reference> value) {
    super.setAccount(value);
  }

  @WorkaroundProperty("hospitalization")
  public EncounterHospitalizationComponent getCHospitalization() {
    return super.getHospitalization();
  }

  public void setCHospitalization(EncounterHospitalizationComponent value) {
    super.setHospitalization(value);
  }

  @WorkaroundProperty("location")
  public List<EncounterLocationComponent> getCLocation() {
    return super.getLocation();
  }

  public void setCLocation(List<EncounterLocationComponent> value) {
    super.setLocation(value);
  }

  @WorkaroundProperty("serviceprovider")
  public Reference getCServiceProvider() {
    return super.getServiceProvider();
  }

  public void setCServiceProvider(Reference value) {
    super.setServiceProvider(value);
  }

  @WorkaroundProperty("partof")
  public Reference getCPartOf() {
    return super.getPartOf();
  }

  public void setCPartOf(Reference value) {
    super.setPartOf(value);
  }

  @WorkaroundProperty("text")
  public Narrative getCText() {
    return super.getText();
  }

  public void setCText(Narrative value) {
    super.setText(value);
  }

  @WorkaroundProperty("contained")
  public List<Resource> getCContained() {
    return super.getContained();
  }

  public void setCContained(List<Resource> value) {
    super.setContained(value);
  }

  @WorkaroundProperty("extension")
  public List<Extension> getCExtension() {
    return super.getExtension();
  }

  public void setCExtension(List<Extension> value) {
    super.setExtension(value);
  }

  @WorkaroundProperty("modifierextension")
  public List<Extension> getCModifierExtension() {
    return super.getModifierExtension();
  }

  public void setCModifierExtension(List<Extension> value) {
    super.setModifierExtension(value);
  }

}
