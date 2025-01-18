package org.example.swm.controllers.staff;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.swm.models.StaffMember;
import org.example.swm.models.ViewFactoryModel;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The class Edit staff member.
 */
public class EditStaffMember implements Initializable {
    public TextField name_field;
    public ChoiceBox contract_field;
    public TextField subject_area_field;
    public TextField line_manager_field;
    public Button edit_button;
    public Label error_label;
    public Label success_label;
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * Staff member variable to store details
     */
    StaffMember sm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("INITIALIZE");
        loadStaffMemberData();
        edit_button.setOnAction(e -> editStaffMember());
    }

    /**
     * Load the staff member data into the form
     */
    private void loadStaffMemberData() {
        sm = ViewFactoryModel.getInstance().getViewFactory().getStaffMember();
        System.out.println("staff member in EditStaffMember: " + sm.getName());
        System.out.println(sm.toString());

        contract_field.getItems().clear();
        contract_field.getItems().add("Full time");
        contract_field.getItems().add("Part time");

        name_field.setText(sm.getName());
        contract_field.setValue(sm.getContractType() == 1.0 ? "Full time" : "Part time");
        subject_area_field.setText(sm.getSubjectArea());
        line_manager_field.setText(sm.getLineManager());
    }

    /**
     * Edit staff member.
     *
     */
    private void editStaffMember() {
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
        sm.setName(name);
        sm.setContractType(contractType);
        sm.setSubjectArea(subjectArea);
        sm.setLineManager(lineManager);
        staffService.addStaffMember(sm);
        error_label.setText("");
        success_label.setText("Staff member edited successfully!");

        name_field.clear();
        contract_field.valueProperty().set(null);
        subject_area_field.clear();
        line_manager_field.clear();

        name_field.setDisable(true);
        contract_field.setDisable(true);
        subject_area_field.setDisable(true);
        line_manager_field.setDisable(true);
        edit_button.setDisable(true);
    }
}
