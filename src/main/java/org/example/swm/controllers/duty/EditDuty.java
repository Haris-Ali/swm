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
    public ChoiceBox<DutyType> dutyType_field;
    public ComboBox<String> activityType_field;
    public TextField description_field;
    public ChoiceBox<String> week_field;
    public TextField duration_field;
    public TextField instances_field;
    public Button edit_duty_button;
    public Label error_label;
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

    private static final HashMap<DutyType, List<String>> activityTypeOptions = new HashMap<>();
    //<-***** https://stackoverflow.com/questions/6881224/is-there-a-better-alternative-to-listt-initalization-than-invoking-arrays-asli  - START
    static {
        activityTypeOptions.put(DutyType.ATSR, Arrays.asList("Formal Scheduled Teaching", "Fully Online Delivery", "Dissertation/Projects",
                "Placements/WBL", "PhD Supervision", "DBA Supervision", "Other"));
        activityTypeOptions.put(DutyType.TLR, Arrays.asList("Programme Leader", "Module Leader", "Head of Subject", "Deputy Head of Subject",
                "Associate Professor", "Professor", "Reader", "T&R Contract", "Partnership Manager", "Faculty Equality & Student Inclusion",
                "Faculty Academic Integrity Officer", "Faculty Academic Integrity Support", "Additional Projects, Roles & Similar- Faculty Leads",
                "Research, Innovation & Similar", "Probation", "Partner Programme Liaison", "Partner Module Liaison & Moderation", "Personal  Tutor",
                "Learner Support/assessment support", "Dean's Discretion", "Other"));
        activityTypeOptions.put(DutyType.SA, Arrays.asList("Scholarly, Currency & Development", "Knowledge exchange", "Writing books/journals/conference",
                "Research", "Exhibitions", "Consultancy", "Developing teaching skills", "Community & Public engagement",
                "Liaison with Proff bodies", "CPD", "Other"));
        activityTypeOptions.put(DutyType.Other, Arrays.asList("Other duties - specify", "PSR", "Validations", "MCoA", "Panel Members", "EDI Network Lead",
                "Internal Tier 1 committee", "Faculty Meeting", "Open day- time of in lieu", "Other external meetings",
                "Schools/UTC/Colleges liaison", "Exam Boards"));
    }
    //<-***** https://stackoverflow.com/questions/6881224/is-there-a-better-alternative-to-listt-initalization-than-invoking-arrays-asli  - END

    private static final List<String> weekOptions = Arrays.asList(
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
        List<String> activities = activityTypeOptions.get(dutyType_field.getValue());
        activityType_field.getItems().setAll(activities);
        week_field.getItems().setAll(weekOptions);
    }

    /**
     * Function to update activity options based on duty type
     */
    private void updateActivityOptions() {
        DutyType selectedType = dutyType_field.getValue();
        if (selectedType != null) {
            List<String> activities = activityTypeOptions.get(selectedType);
            activityType_field.getItems().setAll(activities);
        }
    }

    /**
     * Method that updates duty object, calculate hour categories based on duration and instances.
     * First check for workload being less than 1570 before updating duty to staff member and updating files.
     */
    private void editDuty() {
        String dutyType = String.valueOf(dutyType_field.getValue());
        String activityType = activityType_field.getValue();
        String description = description_field.getText();
        String weeks = week_field.getValue();
        String duration = duration_field.getText();
        String instances = instances_field.getText();

        error_label.setText("");
        success_label.setText("");
        if ((dutyType == null || dutyType.isEmpty()) || (activityType == null || activityType.isEmpty())
                || (description == null || description.isEmpty()) || (weeks == null || weeks.isEmpty())
                || (duration == null || duration.isEmpty()) || (instances == null || instances.isEmpty())) {
            error_label.setText("Please enter all fields");
            return;
        }
        if (!duration.chars().allMatch(Character::isDigit) || !instances.chars().allMatch(Character::isDigit)) {
            error_label.setText("Duration and/or instances must be numbers");
            return;
        }

        int prevWorkload = duty.getWorkloadForDuty();
        duty.setHours();
        duty.calculateHourCategories();
        int newWorkload = duty.getWorkloadForDuty();
        int totalWorkload = staffService.getStaffMember(staffId).getTotalWorkload();
        if ((totalWorkload - (prevWorkload - newWorkload)) + newWorkload > 1570) {
            error_label.setText("Total workload exceeding the maximum limit of 1570. Please enter appropriate values for duration and instances");
            return;
        }
        staffService.updateTotalWorkload(staffId, totalWorkload - (prevWorkload - newWorkload));
        dutyService.addDutyAgainstStaffMember(staffId, duty);

        success_label.setText("Duty updated successfully!");
    }
}
