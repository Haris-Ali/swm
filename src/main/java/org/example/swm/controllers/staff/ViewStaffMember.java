package org.example.swm.controllers.staff;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;
import org.example.swm.models.ViewFactoryModel;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewStaffMember implements Initializable {
    /**
     * The Name field.
     */
    public TextField name_field;
    /**
     * The Contract type field.
     */
    public TextField contract_type_field;
    /**
     * The Subject area field.
     */
    public TextField subject_area_field;
    /**
     * The Line manager field.
     */
    public TextField line_manager_field;
    /**
     * The Duties table.
     */
    public TableView<Duty> duties_table;
    /**
     * The Id column.
     */
    public TableColumn idColumn;
    /**
     * The Type column.
     */
    public TableColumn typeColumn;
    /**
     * The Activity type column.
     */
    public TableColumn activityTypeColumn;
    /**
     * The Description column.
     */
    public TableColumn descriptionColumn;
    /**
     * The Weeks column.
     */
    public TableColumn weeksColumn;
    /**
     * The Duration column.
     */
    public TableColumn durationColumn;
    /**
     * The Instances column.
     */
    public TableColumn instancesColumn;
    /**
     * The Action column.
     */
    public TableColumn actionColumn;
    public TableColumn hours_column;
    public TableColumn hourBreakdown_column;
    public TableColumn atsr_column;
    public TableColumn ts_column;
    public TableColumn tlr_column;
    public TableColumn sa_column;
    public TableColumn others_column;
    /**
     * The Add duty button.
     */
    public Button addDuty_button;
    /**
     * The Edit button.
     */
    public Button edit_button;
    /**
     * The Delete button.
     */
    public Button delete_button;
    /**
     * The Delete all duties button.
     */
    public Button deleteAllDuties_button;
    public Label total_workload_label;
    public Label total_atsrworkload_label;
    public Label total_tsworkload_label;
    public Label total_tlrworkload_label;
    public Label total_saworkload_label;
    public Label total_otherworkload_label;
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
    /**
     * The hashmap to store user's duties
     */
    HashMap<Integer, Duty> duties = new HashMap<>();
    /**
     * Staff member variable to store details
     */
    StaffMember sm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStaffMemberData();
        readDutiesFromFile();
        initializeTableColumns();
        edit_button.setOnAction(e -> handleEditStaffMemberAction());
        delete_button.setOnAction(e -> handleDeleteStaffMemberAction());
        addDuty_button.setOnAction(e -> handleAddDutyAction());
        deleteAllDuties_button.setOnAction(e -> handleDeleteAllDutiesAction());
    }

    /**
     * Load the staff member data into the form
     */
    private void loadStaffMemberData() {
        sm = ViewFactoryModel.getInstance().getViewFactory().getStaffMember();

        name_field.setText(sm.getName());
        contract_type_field.setText(sm.getContractType() == 1.0 ? "Full time" : "Part time");
        subject_area_field.setText(sm.getSubjectArea());
        line_manager_field.setText(sm.getLineManager());
    }

    /**
     * Read duties from file and check for existence
     */
    private void readDutiesFromFile() {
        duties = dutyService.getDutiesAgainstStaffMember(sm.getId());
        if (duties.isEmpty()) {
            System.out.println("No staff members found");
        } else {
            duties_table.getItems().clear();
            duties_table.getItems().addAll(duties.values());
        }
    }

    /**
     * Create and populate table rows and columns
     */
    private void initializeTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("dutyType"));
        activityTypeColumn.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weeksColumn.setCellValueFactory(new PropertyValueFactory<>("weeks"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        instancesColumn.setCellValueFactory(new PropertyValueFactory<>("instances"));
        hours_column.setCellValueFactory(new PropertyValueFactory<>("hours"));
        atsr_column.setCellValueFactory(new PropertyValueFactory<>("atsrHours"));
        ts_column.setCellValueFactory(new PropertyValueFactory<>("tsHours"));
        tlr_column.setCellValueFactory(new PropertyValueFactory<>("tlrHours"));
        sa_column.setCellValueFactory(new PropertyValueFactory<>("saHours"));
        others_column.setCellValueFactory(new PropertyValueFactory<>("otherHours"));

        total_atsrworkload_label.setText("Total ATSR Workload: " + sm.getAtsrWorkload());
        total_tsworkload_label.setText("Total TS Workload: " + sm.getTsWorkload());
        total_tlrworkload_label.setText("Total TLR Workload: " + sm.getTlrWorkload());
        total_saworkload_label.setText("Total SA Workload: " + sm.getSaWorkload());
        total_otherworkload_label.setText("Total Other Workload: " + sm.getOtherWorkload());
        total_workload_label.setText("Total Workload: " + sm.getTotalWorkload());

        addActionButtons();
    }

    /**
     * A function to add edit and delete action buttons against each row
     */
    // Reference: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view - START
    private void addActionButtons() {
        Callback<TableColumn<Duty, Void>, TableCell<Duty, Void>> cellFactory = param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {

                editButton.getStyleClass().add("editBtn");
                editButton.setOnAction(e -> {
                    Duty duty = getTableView().getItems().get(getIndex());
                    handleDutyEditAction(duty);
                });

                deleteButton.getStyleClass().add("deleteBtn");
                deleteButton.setOnAction(e -> {
                    Duty duty = getTableView().getItems().get(getIndex());
                    handleDeleteDutyAction(duty);
                });

                HBox buttons = new HBox(5, editButton, deleteButton);
                setGraphic(buttons);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(new HBox(5, editButton, deleteButton));
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }
    // Reference: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view - END

    /**
     * Function to handle staff member edit button
     */
    private void handleEditStaffMemberAction() {
        ViewFactoryModel.getInstance().getViewFactory().setStaffMember(sm);
        ViewFactoryModel.getInstance().getViewFactory().getSelectedMenuItem().set("EditStaffMember");
    }

    /**
     * Function to handle staff member delete button
     */
    private void handleDeleteStaffMemberAction() {
        dutyService.removeAllDutiesAgainstStaffMember(sm.getId());
        staffService.removeStaffMember(sm.getId());
        ViewFactoryModel.getInstance().getViewFactory().getSelectedMenuItem().set("ViewStaffMembers");
    }

    /**
     * Function to handle add new duty button
     */
    private void handleAddDutyAction() {
        ViewFactoryModel.getInstance().getViewFactory().getSelectedMenuItem().set("AddDuty");
    }

    /**
     * Function to handle actions for duty
     *
     * @param duty the staff member's duty
     */
    private void handleDutyEditAction(Duty duty) {
        ViewFactoryModel.getInstance().getViewFactory().setDuty(duty);
        ViewFactoryModel.getInstance().getViewFactory().getSelectedMenuItem().set("EditDuty");
    }

    /**
     * Function to handle delete button click for duty
     * @param duty the staff member duty
     */
    private void handleDeleteDutyAction(Duty duty) {
        duties_table.getItems().remove(duty);
        dutyService.removeDuty(sm.getId(), duty);
        staffService.updateTotalWorkload(sm.getId(), sm.getTotalWorkload() - duty.getWorkloadForDuty());
    }

    /**
     * Function to handle delete all staff member duties
     */
    private void handleDeleteAllDutiesAction() {
        duties_table.getItems().clear();
        dutyService.removeAllDutiesAgainstStaffMember(sm.getId());
        staffService.updateTotalWorkload(sm.getId(), 0);
    }
}
