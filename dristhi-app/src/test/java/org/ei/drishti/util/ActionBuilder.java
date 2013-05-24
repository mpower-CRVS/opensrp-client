package org.ei.drishti.util;

import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.ActionData;
import org.ei.drishti.dto.AlertStatus;
import org.ei.drishti.dto.BeneficiaryType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.dto.ActionData.*;
import static org.ei.drishti.dto.BeneficiaryType.child;
import static org.ei.drishti.dto.BeneficiaryType.mother;

public class ActionBuilder {
    public static Action actionForCreateAlert(String caseID, String alertStatus, String beneficiaryType, String scheduleName, String visitCode, String startDate, String expiryDate, String index) {
        return new Action(caseID, "alert", "createAlert", createAlert(BeneficiaryType.from(beneficiaryType), scheduleName, visitCode, AlertStatus.from(alertStatus), new DateTime(startDate), new DateTime(expiryDate)).data(), index, true, new HashMap<String, String>());
    }

    public static Action actionForCloseAlert(String caseID, String visitCode, String completionDate, String index) {
        return new Action(caseID, "alert", "closeAlert", markAlertAsClosed(visitCode, completionDate).data(), index, true, new HashMap<String, String>());
    }

    public static Action actionForDeleteAllAlert(String caseID) {
        return new Action(caseID, "alert", "deleteAllAlerts", new HashMap<String, String>(), "0", true, new HashMap<String, String>());
    }

    public static Action actionForCreateChild(String motherCaseId) {
        return new Action("Case X", "child", "register", registerChildBirth(motherCaseId, "TC 1", LocalDate.now(), "female", new HashMap<String, String>()).data(), "0", true, new HashMap<String, String>());
    }

    public static Action actionForUpdateANCOutcome(String caseId, Map<String, String> details) {
        ActionData actionData = updateANCOutcome(details);
        return new Action(caseId, "mother", "updateANCOutcome", actionData.data(), "0", true, actionData.details());
    }

    public static Action actionForMotherPNCVisit(String caseId, Map<String, String> details) {
        ActionData actionData = pncVisitHappened(mother, LocalDate.parse("2012-01-01"), 1, "10", details);
        return new Action(caseId, "mother", "pncVisitHappened", actionData.data(), "0", true, actionData.details());
    }

    public static Action actionForChildPNCVisit(String caseId, Map<String, String> details) {
        ActionData actionData = pncVisitHappened(child, LocalDate.parse("2012-01-01"), 1, "10", details);
        return new Action(caseId, "child", "pncVisitHappened", actionData.data(), "0", true, actionData.details());
    }

    public static Action updateImmunizations(String caseId, Map<String, String> details) {
        ActionData actionData = ActionData.updateImmunizations("bcg opv_0", LocalDate.parse("2012-01-01"), "1", details);
        return new Action(caseId, "child", "updateImmunizations", actionData.data(), "2012-01-01", true, details);
    }

    public static Action closeChild(String caseId) {
        ActionData actionData = ActionData.deleteChild();
        return new Action(caseId, "child", "deleteChild", actionData.data(), "2012-01-01", true, new HashMap<String, String>());
    }

    public static Action actionForReport(String indicator, String annualTarget) {
        ActionData actionData = ActionData.reportForIndicator(indicator, annualTarget, "some-month-summary-json");
        return new Action("", "report", indicator, actionData.data(), "2012-01-01", true, new HashMap<String, String>());
    }
}
