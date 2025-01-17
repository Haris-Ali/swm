package org.example.swm.controllers.staff;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.swm.models.StaffMember;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The class View staff members.
 */
public class ViewStaffMembers implements Initializable {
    public TableView staffMember_table;
    public TextField search_bar;
    public Button search_button;

    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Staff members.
     */
    HashMap<Integer, StaffMember> staffMembers = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readFromFile();
        search_button.setOnAction(e -> searchQuery());
    }

    /**
     * Read staff members from file and check for existence
     */
    private void readFromFile() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) System.out.println("No staff members found");
        else populateTable();
    }

    /**
     * Create and populate table rows and columns
     */
    private void populateTable() {
        TableColumn<StaffMember, String> column1 = new TableColumn<>("ID");
        TableColumn<StaffMember, String> column2 = new TableColumn<>("Name");
        TableColumn<StaffMember, String> column3 = new TableColumn<>("Contract Type");
        TableColumn<StaffMember, String> column4 = new TableColumn<>("Subject Area");
        TableColumn<StaffMember, String> column5 = new TableColumn<>("Line Manager");
        TableColumn<StaffMember, String> column6 = new TableColumn<>("Total Workload");

        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("contractType"));
        column4.setCellValueFactory(new PropertyValueFactory<>("subjectArea"));
        column5.setCellValueFactory(new PropertyValueFactory<>("lineManager"));
        column6.setCellValueFactory(new PropertyValueFactory<>("totalWorkload"));

        staffMember_table.getColumns().addAll(column1, column2, column3, column4, column5, column6);
        for (StaffMember staffMember : staffMembers.values()) {
            staffMember_table.getItems().add(staffMember);
        }
    }

    /**
     * Function to search for staff members based on id or name
     */
    private void searchQuery() {
        String query = search_bar.getText().toLowerCase();
        if (!query.isEmpty()) {
            staffMember_table.getItems().clear();
            for (StaffMember staffMember : staffMembers.values()) {
                if (String.valueOf(staffMember.getId()).contains(query) || staffMember.getName().toLowerCase().contains(query)) {
                    staffMember_table.getItems().add(staffMember);
                }
            }
        } else {
            readFromFile();
        }
    }
}
