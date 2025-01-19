package org.example.swm.controllers.staff;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.swm.models.StaffMember;
import org.example.swm.models.ViewFactoryModel;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import javafx.util.Callback;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The class View staff members.
 */
public class ViewStaffMembers implements Initializable {
    /**
     * The Staff member table.
     */
    @SuppressWarnings("")
    public TableView<StaffMember> staffMember_table;
    /**
     * The Search bar.
     */
    public TextField search_bar;
    /**
     * The Search button.
     */
    public Button search_button;
    /**
     * The Id column.
     */
    public TableColumn idColumn;
    /**
     * The Name column.
     */
    public TableColumn nameColumn;
    /**
     * The Contract type column.
     */
    public TableColumn contractTypeColumn;
    /**
     * The Subject area column.
     */
    public TableColumn subjectAreaColumn;
    /**
     * The Line manager column.
     */
    public TableColumn lineManagerColumn;
    /**
     * The Total workload column.
     */
    public TableColumn totalWorkloadColumn;
    /**
     * The Action column.
     */
    public TableColumn actionColumn;
    /**
     * The Delete all button.
     */
    public Button delete_all_button;

    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
    /**
     * The Staff members.
     */
    HashMap<Integer, StaffMember> staffMembers = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readFromFile();
        initializeTableColumns();
        search_button.setOnAction(e -> searchQuery());
        delete_all_button.setOnAction(e -> handleDeleteAllAction());
    }

    /**
     * Read staff members from file and check for existence
     */
    private void readFromFile() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) {
            System.out.println("No staff members found");
        } else {
            staffMember_table.getItems().clear();
            staffMember_table.getItems().addAll(staffMembers.values());
        }
    }

    /**
     * Create and populate table rows and columns
     */
    private void initializeTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contractTypeColumn.setCellValueFactory(new PropertyValueFactory<>("contractType"));
        subjectAreaColumn.setCellValueFactory(new PropertyValueFactory<>("subjectArea"));
        lineManagerColumn.setCellValueFactory(new PropertyValueFactory<>("lineManager"));
        totalWorkloadColumn.setCellValueFactory(new PropertyValueFactory<>("totalWorkload"));

        addActionButtons();
    }

    /**
     * A function to add edit and delete action buttons against each row
     */
    // Reference: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view - START
    private void addActionButtons() {
        Callback<TableColumn<StaffMember, Void>, TableCell<StaffMember, Void>> cellFactory = param -> new TableCell<>() {
            private final Button viewStaffMemberButton = new Button("View Details");
            private final Button editButton = new Button("Edit");
            private final Button viewWorkloadButton = new Button("View Workload");
            private final Button deleteButton = new Button("Delete");

            {
                viewStaffMemberButton.setOnAction(e -> {
                    StaffMember staffMember = getTableView().getItems().get(getIndex());
                    handleStaffAction(staffMember, "ViewStaffMember");
                });

                editButton.getStyleClass().add("editBtn");
                editButton.setOnAction(e -> {
                    StaffMember staffMember = getTableView().getItems().get(getIndex());
                    handleStaffAction(staffMember, "EditStaffMember");
                });

                viewWorkloadButton.setOnAction(e -> {
                    StaffMember staffMember = getTableView().getItems().get(getIndex());
                    handleStaffAction(staffMember, "ViewWorkload");
                });

                deleteButton.getStyleClass().add("deleteBtn");
                deleteButton.setOnAction(e -> {
                    StaffMember staffMember = getTableView().getItems().get(getIndex());
                    handleDeleteAction(staffMember);
                });

                HBox buttons = new HBox(5, viewStaffMemberButton, editButton, viewWorkloadButton, deleteButton);
                setGraphic(buttons);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(new HBox(5, viewStaffMemberButton, editButton, viewWorkloadButton, deleteButton));
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }
    // Reference: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view - END

    /**
     * Function to handle actions for staff member
     * @param staffMember the staff member
     * @param action the action to be performed (e.g., "ViewStaffMember", "AddDuty", "EditStaffMember")
     */
    private void handleStaffAction(StaffMember staffMember, String action) {
        ViewFactoryModel.getInstance().getViewFactory().setStaffMember(staffMember);
        ViewFactoryModel.getInstance().getViewFactory().getSelectedMenuItem().set(action);
    }

    /**
     * Function to handle delete button click for staff member
     * @param staffMember the staff member
     */
    private void handleDeleteAction(StaffMember staffMember) {
        staffMember_table.getItems().remove(staffMember);
        dutyService.removeAllDutiesAgainstStaffMember(staffMember.getId());
        staffService.removeStaffMember(staffMember.getId());
    }

    /**
     * Function to handle delete all staff members
     */
    private void handleDeleteAllAction() {
        staffMember_table.getItems().clear();
        dutyService.removeAllDuties();
        staffService.removeAllStaffMembers();
    }

    /**
     * Function to search for staff members based on id or name
     */
    private void searchQuery() {
        String query = search_bar.getText().toLowerCase();
        staffMember_table.getItems().clear();
        if (!query.isEmpty()) {
            staffMember_table.getItems().clear();
            for (StaffMember staffMember : staffMembers.values()) {
                if (String.valueOf(staffMember.getId()).contains(query) || staffMember.getName().toLowerCase().contains(query)) {
                    staffMember_table.getItems().add(staffMember);
                }
            }
        } else {
            staffMember_table.getItems().addAll(staffMembers.values());
        }
    }
}
