package org.example.swm.controllers.duty;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;
import org.example.swm.models.ViewFactoryModel;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.*;

/**
 * The class Edit duty.
 */
public class EditDuty implements Initializable {
    /**
     * The Duty type field.
     */
    public ChoiceBox<DutyType> dutyType_field;
    /**
     * The Activity type field.
     */
    public ComboBox<String> activityType_field;
    /**
     * The Description field.
     */
    public TextField description_field;
    /**
     * The Week field.
     */
    public ChoiceBox<String> week_field;
    /**
     * The Duration field.
     */
    public TextField duration_field;
    /**
     * The Instances field.
     */
    public TextField instances_field;
    /**
     * The Edit duty button.
     */
    public Button edit_duty_button;
    /**
     * The Error label.
     */
    public Label error_label;
    /**
     * The Success label.
     */
    public Label success_label;
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
    /**
     * The Staff id.
     */
    int staffId = -1;
    /**
     * The Duty id.
     */
    int dutyId = -1;

    /**
     * The Duty.
     */
    Duty duty;

    private static final HashMap<DutyType, List<String>> ACTIVITY_TYPE_OPTIONS = new HashMap<>();
    //<-***** https://stackoverflow.com/questions/6881224/is-there-a-better-alternative-to-listt-initalization-than-invoking-arrays-asli  - START
    static {
        ACTIVITY_TYPE_OPTIONS.put(DutyType.ATSR, Arrays.asList("Formal Scheduled Teaching", "Fully Online Delivery", "Dissertation/Projects",
                "Placements/WBL", "PhD Supervision", "DBA Supervision", "Other"));
        ACTIVITY_TYPE_OPTIONS.put(DutyType.TLR, Arrays.asList("Programme Leader", "Module Leader", "Head of Subject", "Deputy Head of Subject",
                "Associate Professor", "Professor", "Reader", "T&R Contract", "Partnership Manager", "Faculty Equality & Student Inclusion",
                "Faculty Academic Integrity Officer", "Faculty Academic Integrity Support", "Additional Projects, Roles & Similar- Faculty Leads",
                "Research, Innovation & Similar", "Probation", "Partner Programme Liaison", "Partner Module Liaison & Moderation", "Personal  Tutor",
                "Learner Support/assessment support", "Dean's Discretion", "Other"));
        ACTIVITY_TYPE_OPTIONS.put(DutyType.SA, Arrays.asList("Scholarly, Currency & Development", "Knowledge exchange", "Writing books/journals/conference",
                "Research", "Exhibitions", "Consultancy", "Developing teaching skills", "Community & Public engagement",
                "Liaison with Proff bodies", "CPD", "Other"));
        ACTIVITY_TYPE_OPTIONS.put(DutyType.Other, Arrays.asList("Other duties - specify", "PSR", "Validations", "MCoA", "Panel Members", "EDI Network Lead",
                "Internal Tier 1 committee", "Faculty Meeting", "Open day- time of in lieu", "Other external meetings",
                "Schools/UTC/Colleges liaison", "Exam Boards"));
    }
    //<-***** https://stackoverflow.com/questions/6881224/is-there-a-better-alternative-to-listt-initalization-than-invoking-arrays-asli  - END

    private static final List<String> WEEK_OPTIONS = Arrays.asList(
            "Trimester 1", "Trimester 2", "Trimester 3", "All Year"
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        duty = ViewFactoryModel.getInstance().getViewFactory().getDuty();
        dutyId = duty.getId();
        staffId = duty.getStaffId();

        loadDutyData();
        edit_duty_button.setOnAction(e -> editDuty());
    }

    /**
     * Function to load and display existing duty data
     */
    private void loadDutyData() {
        dutyType_field.setValue(DutyType.valueOf(duty.getDutyType()));
        activityType_field.setValue(duty.getActivityType());
        description_field.setText(duty.getDescription());
        week_field.setValue(duty.getWeeks());
        duration_field.setText(String.valueOf(duty.getDuration()));
        instances_field.setText(String.valueOf(duty.getInstances()));

        dutyType_field.getItems().setAll(DutyType.values());
        dutyType_field.setOnAction(e -> updateActivityOptions());
        updateActivityOptions();
        week_field.getItems().setAll(WEEK_OPTIONS);
    }

    /**
     * Function to update activity options based on duty type
     */
    private void updateActivityOptions() {
        DutyType selectedType = dutyType_field.getValue();
        List<String> activities = ACTIVITY_TYPE_OPTIONS.getOrDefault(selectedType, Collections.emptyList());
        activityType_field.getItems().setAll(activities);
    }

    /**
     * Method that updates duty object, calculate hour categories based on duration and instances.
     * First check for workload being less than 1570 before updating duty to staff member and updating files.
     */
    private void editDuty() {
        resetLabels();
        if (!isInputValid()) return;

        String dutyType = String.valueOf(dutyType_field.getValue());
        String activityType = activityType_field.getValue();
        String description = description_field.getText();
        String weeks = week_field.getValue();
        int duration = Integer.parseInt(duration_field.getText());
        int instances = Integer.parseInt(instances_field.getText());

        int prevWorkload = duty.getWorkloadForDuty();
        updateDuty(dutyType, activityType, description, weeks, duration, instances);

        if (isWorkloadExceeding(prevWorkload)) return;

        updateStaffWorkload(prevWorkload);
        dutyService.addDutyAgainstStaffMember(staffId, duty);
        success_label.setText("Duty updated successfully!");
    }

    /**
     * Function to update the duty's properties
     * @param dutyType duty type
     * @param activityType activity type
     * @param description description
     * @param weeks weeks/year
     * @param duration duration hours in weeks
     * @param instances instances
     */
    private void updateDuty(String dutyType, String activityType, String description, String weeks, int duration, int instances) {
        duty.setDutyType(dutyType);
        duty.setActivityType(activityType);
        duty.setDescription(description);
        duty.setWeeks(weeks);
        duty.setDuration(duration);
        duty.setInstances(instances);
        duty.setHours();
        duty.calculateHourCategories();
    }

    /**
     * Function to check if updated workload exceeds 1570 or not
     * @param prevWorkload previous workload before editing duty
     * @return true/false to indicate if workload exceeds or not
     */
    private boolean isWorkloadExceeding(int prevWorkload) {
        int totalWorkload = staffService.getStaffMember(staffId).getTotalWorkload();
        int newWorkload = duty.getWorkloadForDuty();
        if ((totalWorkload - prevWorkload) + newWorkload > 1570) {
            error_label.setText("Total workload exceeding the maximum limit of 1570. Please enter appropriate values for duration and instances");
            return true;
        }
        return false;
    }

    /**
     * Function to update the staff member's workload values
     * @param prevWorkload the previous workload before editing duty
     */
    private void updateStaffWorkload(int prevWorkload) {
        StaffMember staffMember = staffService.getStaffMember(staffId);
        staffService.updateTotalWorkload(staffId, staffMember.getTotalWorkload() - prevWorkload + duty.getWorkloadForDuty());
        staffService.updateAtsrWorkload(staffId, staffMember.getAtsrWorkload() - duty.getAtsrHours());
        staffService.updateTsWorkload(staffId, staffMember.getTsWorkload() - duty.getTsHours());
        staffService.updateTlrWorkload(staffId, staffMember.getTlrWorkload() - duty.getTlrHours());
        staffService.updateSaWorkload(staffId, staffMember.getSaWorkload() - duty.getSaHours());
        staffService.updateOtherWorkload(staffId, staffMember.getOtherWorkload() - duty.getOtherHours());
    }

    /**
     * Functio to validate inputs for null and number values
     * @return true/false to indicate if form is valid or not
     */
    private boolean isInputValid() {
        if (dutyType_field.getValue() == null || activityType_field.getValue() == null ||
                description_field.getText().isEmpty() || week_field.getValue() == null ||
                duration_field.getText().isEmpty() || instances_field.getText().isEmpty()) {
            error_label.setText("Please enter all fields");
            return false;
        }
        if (!duration_field.getText().chars().allMatch(Character::isDigit) ||
                !instances_field.getText().chars().allMatch(Character::isDigit)) {
            error_label.setText("Duration and/or instances must be numbers");
            return false;
        }
        return true;
    }

    /**
     * Resets the error and success labels
     */
    private void resetLabels() {
        error_label.setText("");
        success_label.setText("");
    }
}
