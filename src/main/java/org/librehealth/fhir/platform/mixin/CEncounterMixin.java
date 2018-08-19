package org.librehealth.fhir.platform.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hl7.fhir.dstu3.model.Account;
import org.hl7.fhir.dstu3.model.Appointment;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.Encounter.EncounterLocationComponent;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.librehealth.fhir.platform.model.CEncounter;

import java.util.List;

public interface CEncounterMixin {

  /**
   * @see CEncounter#getAppointmentTarget()
   */
  @JsonIgnore
  Appointment getAppointmentTarget();

  /**
   * @see CEncounter#getPartOfTarget()
   */
  @JsonIgnore
  Encounter getPartOfTarget();

  /**
   * @see CEncounter#getEpisodeOfCareTarget()
   */
  @JsonIgnore
  List<EpisodeOfCare> getEpisodeOfCareTarget();

  /**
   * @see CEncounter#getIncomingReferralTarget()
   */
  @JsonIgnore
  List<ReferralRequest> getIncomingReferralTarget();

  /**
   * @see CEncounter#getAccountTarget()
   */
  @JsonIgnore
  List<Account> getAccountTarget();

  /**
   * @see CEncounter#getServiceProviderTarget()
   */
  @JsonIgnore
  Organization getServiceProviderTarget();

  /**
   * @see CEncounter#getIdentifierFirstRep()
   */
  @JsonIgnore
  Identifier getIdentifierFirstRep();

  /**
   * @see CEncounter#getStatusHistoryFirstRep()
   */
  @JsonIgnore
  Encounter.StatusHistoryComponent getStatusHistoryFirstRep();

  /**
   * @see CEncounter#getClassHistoryFirstRep()
   */
  @JsonIgnore
  Encounter.ClassHistoryComponent getClassHistoryFirstRep();

  /**
   * @see CEncounter#getTypeFirstRep()
   */
  @JsonIgnore
  CodeableConcept getTypeFirstRep();

  /**
   * @see CEncounter#getEpisodeOfCareFirstRep()
   */
  @JsonIgnore
  Reference getEpisodeOfCareFirstRep();

  /**
   * @see CEncounter#getIncomingReferralFirstRep()
   */
  @JsonIgnore
  Reference getIncomingReferralFirstRep();

  /**
   * @see CEncounter#getParticipantFirstRep()
   */
  @JsonIgnore
  Encounter.EncounterParticipantComponent getParticipantFirstRep();

  /**
   * @see CEncounter#getReasonFirstRep()
   */
  @JsonIgnore
  CodeableConcept getReasonFirstRep();

  /**
   * @see CEncounter#getDiagnosisFirstRep()
   */
  @JsonIgnore
  Encounter.DiagnosisComponent getDiagnosisFirstRep();

  /**
   * @see CEncounter#getAccountFirstRep()
   */
  @JsonIgnore
  Reference getAccountFirstRep();

  /**
   * @see CEncounter#getLocationFirstRep()
   */
  @JsonIgnore
  EncounterLocationComponent getLocationFirstRep();

}
