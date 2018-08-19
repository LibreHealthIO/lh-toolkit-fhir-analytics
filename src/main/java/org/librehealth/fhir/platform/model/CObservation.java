package org.librehealth.fhir.platform.model;

import ca.uhn.fhir.context.FhirVersionEnum;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.Base;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Extension;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.InstantType;
import org.hl7.fhir.dstu3.model.Meta;
import org.hl7.fhir.dstu3.model.Narrative;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Quantity;
import org.hl7.fhir.dstu3.model.Range;
import org.hl7.fhir.dstu3.model.Ratio;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.ResourceType;
import org.hl7.fhir.dstu3.model.SampledData;
import org.hl7.fhir.dstu3.model.Specimen;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.TimeType;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.exceptions.FHIRException;
import org.librehealth.fhir.platform.annotation.WorkaroundProperty;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table("Observation")
@AccessType(AccessType.Type.PROPERTY)
public class CObservation extends Observation {

  @Id
  public String getObservationId() {
    return getIdElement().getIdPart();
  }

  public void setObservationId(String value) {
    IdType currentId = getIdElement();
    IdType newId = new IdType(currentId.getBaseUrl(), getResourceType().getPath(), value, currentId.getVersionIdPart());
    setIdElement(newId);
  }

  @Transient
  @Override
  public Identifier getIdentifierFirstRep() {
    return super.getIdentifierFirstRep();
  }

  @Transient
  @Override
  public List<Reference> getBasedOn() {
    return super.getBasedOn();
  }

  @WorkaroundProperty("basedOn")
  public List<Reference> getCBasedOn() {
    return super.getBasedOn();
  }

  public void setCBasedOn(List<Reference> value) {
    super.setBasedOn(value);
  }

  @Transient
  @Override
  public Reference getBasedOnFirstRep() {
    return super.getBasedOnFirstRep();
  }

  @Transient
  @Override
  public List<Resource> getBasedOnTarget() {
    return super.getBasedOnTarget();
  }

  @Transient
  @Override
  public Enumeration<ObservationStatus> getStatusElement() {
    return super.getStatusElement();
  }

  @Transient
  @Override
  public ObservationStatus getStatus() {
    return super.getStatus();
  }

  @Transient
  @Override
  public List<CodeableConcept> getCategory() {
    return super.getCategory();
  }

  @WorkaroundProperty("category")
  public List<CodeableConcept> getCCategory() {
    return super.getCategory();
  }

  public void setCCategory(List<CodeableConcept> value) {
    super.setCategory(value);
  }

  @Transient
  @Override
  public CodeableConcept getCategoryFirstRep() {
    return super.getCategoryFirstRep();
  }

  @Transient
  @Override
  public CodeableConcept getCode() {
    return super.getCode();
  }

  public CodeableConcept getCCode() {
    return super.getCode();
  }

  public void setCCode(CodeableConcept value) {
    super.setCode(value);
  }

  @Transient
  @Override
  public Reference getSubject() {
    return super.getSubject();
  }

  @WorkaroundProperty("subject")
  public Reference getCSubject() {
    return super.getSubject();
  }

  public void setCSubject(Reference value) {
    super.setSubject(value);
  }

  @Transient
  @Override
  public Resource getSubjectTarget() {
    return super.getSubjectTarget();
  }

  @Transient
  @Override
  public Reference getContext() {
    return super.getContext();
  }

  @WorkaroundProperty("context")
  public Reference getCContext() {
    return super.getContext();
  }

  public void setCContext(Reference value) {
    super.setContext(value);
  }

  @Transient
  @Override
  public Resource getContextTarget() {
    return super.getContextTarget();
  }

  @Transient
  @Override
  public Type getEffective() {
    return super.getEffective();
  }

//  @WorkaroundProperty("effective")
//  public Type getCEffective() {
//    return super.getEffective();
//  }
//
//  public void setCEffective(Type value) {
//    super.setEffective(value);
//  }

  @Transient
  @Override
  public DateTimeType getEffectiveDateTimeType() throws FHIRException {
    return super.getEffectiveDateTimeType();
  }

  @Transient
  @Override
  public Period getEffectivePeriod() throws FHIRException {
    return super.getEffectivePeriod();
  }

  @Transient
  @Override
  public InstantType getIssuedElement() {
    return super.getIssuedElement();
  }

  @Transient
  @Override
  public Date getIssued() {
    return super.getIssued();
  }

  @Transient
  @Override
  public List<Reference> getPerformer() {
    return super.getPerformer();
  }

  @Transient
  @Override
  public Reference getPerformerFirstRep() {
    return super.getPerformerFirstRep();
  }

  @Transient
  @Override
  public List<Resource> getPerformerTarget() {
    return super.getPerformerTarget();
  }

  @Transient
  @Override
  public Type getValue() {
    return super.getValue();
  }

  @Transient
  @Override
  public Quantity getValueQuantity() throws FHIRException {
    return super.getValueQuantity();
  }

  @Transient
  @Override
  public CodeableConcept getValueCodeableConcept() throws FHIRException {
    return super.getValueCodeableConcept();
  }

  @Transient
  @Override
  public StringType getValueStringType() throws FHIRException {
    return super.getValueStringType();
  }

  @Transient
  @Override
  public BooleanType getValueBooleanType() throws FHIRException {
    return super.getValueBooleanType();
  }

  @Transient
  @Override
  public Range getValueRange() throws FHIRException {
    return super.getValueRange();
  }

  @Transient
  @Override
  public Ratio getValueRatio() throws FHIRException {
    return super.getValueRatio();
  }

  @Transient
  @Override
  public SampledData getValueSampledData() throws FHIRException {
    return super.getValueSampledData();
  }

  @Transient
  @Override
  public Attachment getValueAttachment() throws FHIRException {
    return super.getValueAttachment();
  }

  @Transient
  @Override
  public TimeType getValueTimeType() throws FHIRException {
    return super.getValueTimeType();
  }

  @Transient
  @Override
  public DateTimeType getValueDateTimeType() throws FHIRException {
    return super.getValueDateTimeType();
  }

  @Transient
  @Override
  public Period getValuePeriod() throws FHIRException {
    return super.getValuePeriod();
  }

  @Transient
  @Override
  public CodeableConcept getDataAbsentReason() {
    return super.getDataAbsentReason();
  }

  @Transient
  @Override
  public CodeableConcept getInterpretation() {
    return super.getInterpretation();
  }

  @Transient
  @Override
  public StringType getCommentElement() {
    return super.getCommentElement();
  }

  @Transient
  @Override
  public String getComment() {
    return super.getComment();
  }

  @Transient
  @Override
  public CodeableConcept getBodySite() {
    return super.getBodySite();
  }

  @WorkaroundProperty("bodysite")
  public CodeableConcept getCBodySite() {
    return super.getBodySite();
  }

  public void setCBodySite(CodeableConcept value) {
    super.setBodySite(value);
  }

  @Transient
  @Override
  public CodeableConcept getMethod() {
    return super.getMethod();
  }

  @Transient
  @Override
  public Reference getSpecimen() {
    return super.getSpecimen();
  }

  @Transient
  @Override
  public Specimen getSpecimenTarget() {
    return super.getSpecimenTarget();
  }

  @Transient
  @Override
  public Reference getDevice() {
    return super.getDevice();
  }

  @Transient
  @Override
  public Resource getDeviceTarget() {
    return super.getDeviceTarget();
  }

  @Transient
  @Override
  public List<ObservationReferenceRangeComponent> getReferenceRange() {
    return super.getReferenceRange();
  }

  @Transient
  @Override
  public ObservationReferenceRangeComponent getReferenceRangeFirstRep() {
    return super.getReferenceRangeFirstRep();
  }

  @Transient
  @Override
  public List<ObservationRelatedComponent> getRelated() {
    return super.getRelated();
  }

  @Transient
  @Override
  public ObservationRelatedComponent getRelatedFirstRep() {
    return super.getRelatedFirstRep();
  }

  @Transient
  @Override
  public List<ObservationComponentComponent> getComponent() {
    return super.getComponent();
  }

  @Transient
  @Override
  public ObservationComponentComponent getComponentFirstRep() {
    return super.getComponentFirstRep();
  }

  @Transient
  @Override
  public Base[] getProperty(int hash, String name, boolean checkValid) throws FHIRException {
    return super.getProperty(hash, name, checkValid);
  }

  @Transient
  @Override
  public String[] getTypesForProperty(int hash, String name) throws FHIRException {
    return super.getTypesForProperty(hash, name);
  }

  @Transient
  @Override
  public ResourceType getResourceType() {
    return super.getResourceType();
  }

  @Transient
  public Map<String, Object> getUserData() {
    return new HashMap<>();
  }

  @Transient
  @Override
  public String getIdBase() {
    return super.getIdBase();
  }

  @Transient
  @Override
  public UriType getImplicitRulesElement() {
    return super.getImplicitRulesElement();
  }

  @Transient
  @Override
  public boolean isPrimitive() {
    return super.isPrimitive();
  }

  @Transient
  @Override
  public boolean isResource() {
    return super.isResource();
  }

  @Transient
  @Override
  public boolean isEmpty() {
    return super.isEmpty();
  }

  @Transient
  @Override
  public FhirVersionEnum getStructureFhirVersionEnum() {
    return super.getStructureFhirVersionEnum();
  }

  @Transient
  @Override
  public CodeType getLanguageElement() {
    return super.getLanguageElement();
  }

  @Transient
  @Override
  public IdType getIdElement() {
    return super.getIdElement();
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

  @WorkaroundProperty("performer")
  public List<Reference> getCPerformer() {
    return super.getPerformer();
  }

  public void setCPerformer(List<Reference> value) {
    super.setPerformer(value);
  }

//  @WorkaroundProperty("value")
//  public Type getCValue() {
//    return super.getValue();
//  }
//
//  public void setCValue(Type value) {
//    super.setValue(value);
//  }

  @WorkaroundProperty("dataabsentreason")
  public CodeableConcept getCDataAbsentReason() {
    return super.getDataAbsentReason();
  }

  public void setCDataAbsentReason(CodeableConcept value) {
    super.setDataAbsentReason(value);
  }

  @WorkaroundProperty("interpretation")
  public CodeableConcept getCInterpretation() {
    return super.getInterpretation();
  }

  public void setCInterpretation(CodeableConcept value) {
    super.setInterpretation(value);
  }

  @WorkaroundProperty("method")
  public CodeableConcept getCMethod() {
    return super.getMethod();
  }

  public void setCMethod(CodeableConcept value) {
    super.setMethod(value);
  }

  @WorkaroundProperty("specimen")
  public Reference getCSpecimen() {
    return super.getSpecimen();
  }

  public void setCSpecimen(Reference value) {
    super.setSpecimen(value);
  }

  @WorkaroundProperty("device")
  public Reference getCDevice() {
    return super.getDevice();
  }

  public void setCDevice(Reference value) {
    super.setDevice(value);
  }

  @WorkaroundProperty("referencerange")
  public List<ObservationReferenceRangeComponent> getCReferenceRange() {
    return super.getReferenceRange();
  }

  public void setCReferenceRange(List<ObservationReferenceRangeComponent> value) {
    super.setReferenceRange(value);
  }

  @WorkaroundProperty("related")
  public List<ObservationRelatedComponent> getCRelated() {
    return super.getRelated();
  }

  public void setCRelated(List<ObservationRelatedComponent> value) {
    super.setRelated(value);
  }

  @WorkaroundProperty("component")
  public List<ObservationComponentComponent> getCComponent() {
    return super.getComponent();
  }

  public void setCComponent(List<ObservationComponentComponent> value) {
    super.setComponent(value);
  }
}
