package org.librehealth.fhir.platform.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class  GeneralUtils {

  private static final GeneralUtils generalUtils = new GeneralUtils();

  private GeneralUtils() {

  }

  public static GeneralUtils getInstance() {
    return generalUtils;
  }

  public String[] getNullProperties(Object bean) {
    BeanWrapper wrapper = new BeanWrapperImpl(bean);
    return Stream.of(wrapper.getPropertyDescriptors())
            .map(FeatureDescriptor::getName)
            .filter(s -> wrapper.getPropertyValue(s) == null)
            .toArray(String[]::new);
  }

  // TODO: 19-Jul-18 Return errors as OperationOutcome objects complying with FHIR standards
//  private OperationOutcome getOperationOutcome(OperationOutcome.IssueSeverity severity, OperationOutcome.IssueType type, String diagnostics) {
//    OperationOutcome outcome = new OperationOutcome();
//    OperationOutcome.OperationOutcomeIssueComponent issue = new OperationOutcome.OperationOutcomeIssueComponent();
//    issue.setSeverity(severity);
//    issue.setCode(type);
//    issue.setDiagnostics(diagnostics);
//    outcome.setIssue(Collections.singletonList(issue));
//    return outcome;
//  }
}
