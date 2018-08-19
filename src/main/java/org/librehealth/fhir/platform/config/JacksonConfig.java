package org.librehealth.fhir.platform.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.BackboneElement;
import org.hl7.fhir.dstu3.model.Base;
import org.hl7.fhir.dstu3.model.BaseExtension;
import org.hl7.fhir.dstu3.model.BaseNarrative;
import org.hl7.fhir.dstu3.model.BaseReference;
import org.hl7.fhir.dstu3.model.BaseResource;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Element;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Meta;
import org.hl7.fhir.dstu3.model.Narrative;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Quantity;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;
import org.librehealth.fhir.platform.mixin.AttachmentMixin;
import org.librehealth.fhir.platform.mixin.BackboneElementMixin;
import org.librehealth.fhir.platform.mixin.BaseExtensionMixin;
import org.librehealth.fhir.platform.mixin.BaseMixin;
import org.librehealth.fhir.platform.mixin.BaseNarrativeMixin;
import org.librehealth.fhir.platform.mixin.BaseReferenceMixin;
import org.librehealth.fhir.platform.mixin.BaseResourceMixin;
import org.librehealth.fhir.platform.mixin.CEncounterMixin;
import org.librehealth.fhir.platform.mixin.CObservationMixin;
import org.librehealth.fhir.platform.mixin.CPatientMixin;
import org.librehealth.fhir.platform.mixin.CodeableConceptMixin;
import org.librehealth.fhir.platform.mixin.ElementMixin;
import org.librehealth.fhir.platform.mixin.EncounterHospitalizationComponentMixin;
import org.librehealth.fhir.platform.mixin.EncounterLocationComponentMixin;
import org.librehealth.fhir.platform.mixin.IdTypeMixin;
import org.librehealth.fhir.platform.mixin.IdentifierMixin;
import org.librehealth.fhir.platform.mixin.MetaMixin;
import org.librehealth.fhir.platform.mixin.NarrativeMixin;
import org.librehealth.fhir.platform.mixin.OrganizationMixin;
import org.librehealth.fhir.platform.mixin.PeriodMixin;
import org.librehealth.fhir.platform.mixin.QuantityMixin;
import org.librehealth.fhir.platform.mixin.ReferenceMixin;
import org.librehealth.fhir.platform.mixin.ResourceMixin;
import org.librehealth.fhir.platform.mixin.StringTypeMixin;
import org.librehealth.fhir.platform.mixin.TypeMixin;
import org.librehealth.fhir.platform.mixin.XhtmlNodeMixin;
import org.librehealth.fhir.platform.model.CEncounter;
import org.librehealth.fhir.platform.model.CObservation;
import org.librehealth.fhir.platform.model.CPatient;
import org.librehealth.fhir.platform.serializer.XhtmlNodeDeserializer;
import org.librehealth.fhir.platform.serializer.XhtmlNodeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
  public static final ObjectMapper mapper = configureObjectMapper();

  private static ObjectMapper configureObjectMapper() {
    return new ObjectMapper()
            .registerModule(
                    new SimpleModule()
                            .addSerializer(new XhtmlNodeSerializer())
                            .addDeserializer(XhtmlNode.class, new XhtmlNodeDeserializer())
            )
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .addMixIn(Attachment.class, AttachmentMixin.class)
            .addMixIn(BackboneElement.class, BackboneElementMixin.class)
            .addMixIn(Base.class, BaseMixin.class)
            .addMixIn(BaseExtension.class, BaseExtensionMixin.class)
            .addMixIn(BaseNarrative.class, BaseNarrativeMixin.class)
            .addMixIn(BaseReference.class, BaseReferenceMixin.class)
            .addMixIn(BaseResource.class, BaseResourceMixin.class)
            .addMixIn(CEncounter.class, CEncounterMixin.class)
            .addMixIn(CodeableConcept.class, CodeableConceptMixin.class)
            .addMixIn(CObservation.class, CObservationMixin.class)
            .addMixIn(CPatient.class, CPatientMixin.class)
            .addMixIn(Element.class, ElementMixin.class)
            .addMixIn(Identifier.class, IdentifierMixin.class)
            .addMixIn(IdType.class, IdTypeMixin.class)
            .addMixIn(Meta.class, MetaMixin.class)
            .addMixIn(Narrative.class, NarrativeMixin.class)
            .addMixIn(Organization.class, OrganizationMixin.class)
            .addMixIn(Encounter.EncounterHospitalizationComponent.class, EncounterHospitalizationComponentMixin.class)
            .addMixIn(Encounter.EncounterLocationComponent.class, EncounterLocationComponentMixin.class)
            .addMixIn(Period.class, PeriodMixin.class)
            .addMixIn(Quantity.class, QuantityMixin.class)
            .addMixIn(Reference.class, ReferenceMixin.class)
            .addMixIn(Resource.class, ResourceMixin.class)
            .addMixIn(StringType.class, StringTypeMixin.class)
            .addMixIn(Type.class, TypeMixin.class)
            .addMixIn(XhtmlNode.class, XhtmlNodeMixin.class);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return mapper;
  }
}
