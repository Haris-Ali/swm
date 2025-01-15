package org.example.swm.controllers.staff;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.swm.models.StaffMember;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class Add staff member for adding new staff members.
 */
public class AddStaffMember implements Initializable {
    public TextField name_field;
    public ChoiceBox contract_field;
    public TextField subject_area_field;
    public TextField line_manager_field;
    public Button add_button;
    public Label error_label;
    public Label success_label;
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contract_field.getItems().add("Full time");
        contract_field.getItems().add("Part time");
        add_button.setOnAction(e -> addStaffMember());
    }

    /**
     * Method to add new staff member
     */
    private void addStaffMember() {
        String name = name_field.getText();
        String contractValue = (String) contract_field.getValue();
        String subjectArea = subject_area_field.getText();
        String lineManager = line_manager_field.getText();

        error_label.setText("");
        success_label.setText("");
        if (name.isEmpty() || contractValue.isEmpty() || subjectArea.isEmpty() || lineManager.isEmpty()) {
            error_label.setText("All fields are required");
            return;
        }

        double contractType;
        if (contractValue.equals("Full time")) contractType = 1.0;
        else contractType = 0.5;
        StaffMember sm = new StaffMember(name, contractType, subjectArea, lineManager);
        staffService.addStaffMember(sm);
        error_label.setText("");
        success_label.setText("Staff member added successfully!");
    }
}
