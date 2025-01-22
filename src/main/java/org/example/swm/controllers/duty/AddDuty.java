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

public class AddDuty implements Initializable {
    public ChoiceBox<DutyType> dutyType_field;
    public ComboBox<String> activityType_field;
    public TextField description_field;
    public ChoiceBox<String> week_field;
    public TextField duration_field;
    public TextField instances_field;
    public Button add_duty_button;
    public Label error_label;
    public Label success_label;
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Staff member.
     */
    StaffMember staffMember;

    private static final HashMap<DutyType, List<String>> ACTIVITY_TYPE_OPTIONS = new HashMap<>(); // Hashmap to store activity type options
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
        staffMember = ViewFactoryModel.getInstance().getViewFactory().getStaffMember();

        dutyType_field.getItems().setAll(List.of(DutyType.values()));
        week_field.getItems().setAll(WEEK_OPTIONS);

        dutyType_field.setOnAction(e -> setActivityType());
        add_duty_button.setOnAction(e -> addDuty());
    }

    /**
     * Change activity type based on duty type selection
     */
    private void setActivityType() {
        DutyType selectedDutyType = dutyType_field.getValue();
        List<String> activities = ACTIVITY_TYPE_OPTIONS.get(selectedDutyType);
        activityType_field.setDisable(false);
        activityType_field.getItems().setAll(activities);
    }

    /**
     * Method that creates new duty object, calculate hour categories based on duration and instances.
     * First check for workload being less than 1570 before adding duty to staff member and updating files.
     */
    private void addDuty() {
        resetLabels();
        if (!isInputValid()) return;

        Duty duty = createDutyFromInput();
        duty.calculateHourCategories();

        if (isWorkloadExceeding(duty)) {
            error_label.setText("Total workload exceeding the maximum limit of 1570. Please enter appropriate values for duration and instances");
            return;
        }

        updateStaffWorkload(duty);
        dutyService.addDutyAgainstStaffMember(staffMember.getId(), duty);

        success_label.setText("Duty added successfully!");
    }

    /**
     * Function to create a new duty
     * @return the new duty object
     */
    private Duty createDutyFromInput() {
        return new Duty(
                staffMember.getId(),
                String.valueOf(dutyType_field.getValue()),
                activityType_field.getValue(),
                description_field.getText(),
                week_field.getValue(),
                Integer.parseInt(duration_field.getText()),
                Integer.parseInt(instances_field.getText())
        );
    }

    /**
     * Function to check if workload exceeds 1570 or not
     * @return true/false to indicate if workload exceeds or not
     */
    private boolean isWorkloadExceeding(Duty duty) {
        int totalWorkload = staffMember.getTotalWorkload();
        return totalWorkload + duty.getWorkloadForDuty() > 1570;
    }

    /**
     * Function to update the staff member's workload values
     */
    private void updateStaffWorkload(Duty duty) {
        staffService.updateTotalWorkload(staffMember.getId(), staffMember.getTotalWorkload() + duty.getWorkloadForDuty());
        staffService.updateAtsrWorkload(staffMember.getId(), staffMember.getAtsrWorkload() + duty.getAtsrHours());
        staffService.updateTsWorkload(staffMember.getId(), staffMember.getTsWorkload() + duty.getTsHours());
        staffService.updateTlrWorkload(staffMember.getId(), staffMember.getTlrWorkload() + duty.getTlrHours());
        staffService.updateSaWorkload(staffMember.getId(), staffMember.getSaWorkload() + duty.getSaHours());
        staffService.updateOtherWorkload(staffMember.getId(), staffMember.getOtherWorkload() + duty.getOtherHours());
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
