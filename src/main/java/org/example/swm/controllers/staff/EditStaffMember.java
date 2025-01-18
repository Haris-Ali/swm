package org.example.swm.controllers.staff;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.swm.models.StaffMember;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * The class Edit staff member.
 */
public class EditStaffMember implements Initializable {
    public TextField search_bar;
    public Button search_button;
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
     * The Staff members.
     */
    HashMap<Integer, StaffMember> staffMembers;
    /**
     * Staff member variable to store details
     */
    StaffMember sm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readFromFile();
        search_button.setOnAction(e -> findStaffMember());
        edit_button.setOnAction(e -> editStaffMember());
    }

    /**
     * Read staff members from file and check for existence
     */
    private void readFromFile() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) error_label.setText("No staff members found");
    }

    /**
     * Listen to input on search bar and find staff member by matching id or name
     */
    private void findStaffMember() {
        String input = search_bar.getText();
        for (StaffMember staffMember : staffMembers.values()) {
            if (input.equalsIgnoreCase(staffMember.getName()) || (isNumeric(input) && Integer.parseInt(input) == staffMember.getId())) {
                sm = staffMember;
                break;
            }
        }
        if (sm != null) loadStaffMemberData();
        else error_label.setText("No staff member with this id/name found");
    }

    /**
     * If staff member is found, load their data into the form
     */
    private void loadStaffMemberData() {
        name_field.setDisable(false);
        contract_field.setDisable(false);
        subject_area_field.setDisable(false);
        line_manager_field.setDisable(false);

        contract_field.getItems().add("Full time");
        contract_field.getItems().add("Part time");

        name_field.setText(sm.getName());
        contract_field.setValue(sm.getContractType() == 1.0 ? "Full time" : "Part time");
        subject_area_field.setText(sm.getSubjectArea());
        line_manager_field.setText(sm.getLineManager());

        edit_button.setDisable(false);
        error_label.setText("");

        search_bar.setText("");
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
        StaffMember sm = new StaffMember(name, contractType, subjectArea, lineManager);
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

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
