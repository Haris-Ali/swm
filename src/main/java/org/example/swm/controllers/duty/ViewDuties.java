package org.example.swm.controllers.duty;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.example.swm.models.Duty;
import org.example.swm.models.ViewFactoryModel;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The type View duties.
 */
public class ViewDuties implements Initializable {
    /**
     * The Duties table.
     */
    public TableView<Duty> duties_table;
    /**
     * The Id column.
     */
    public TableColumn id_column;
    /**
     * The Staff id column.
     */
    public TableColumn staffId_column;
    /**
     * The Type column.
     */
    public TableColumn type_column;
    /**
     * The Activity column.
     */
    public TableColumn activity_column;
    /**
     * The Description column.
     */
    public TableColumn description_column;
    /**
     * The Weeks column.
     */
    public TableColumn weeks_column;
    /**
     * The Duration column.
     */
    public TableColumn duration_column;
    /**
     * The Instances column.
     */
    public TableColumn instances_column;
    /**
     * The Actions column.
     */
    public TableColumn actions_column;
    /**
     * The Search bar.
     */
    public TextField search_bar;
    /**
     * The Search button.
     */
    public Button search_button;
    /**
     * The Delete all button.
     */
    public Button delete_all_button;
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Duties.
     */
    HashMap<Integer, Duty> duties = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readFromFile();
        initializeTableColumns();
        search_button.setOnAction(e -> searchQuery());
        delete_all_button.setOnAction(e -> handleDeleteAllAction());
    }

    /**
     * Read duties from file and check for existence
     */
    private void readFromFile() {
        duties = dutyService.getAllDuties();
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
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        staffId_column.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        type_column.setCellValueFactory(new PropertyValueFactory<>("dutyType"));
        activity_column.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));
        weeks_column.setCellValueFactory(new PropertyValueFactory<>("weeks"));
        duration_column.setCellValueFactory(new PropertyValueFactory<>("duration"));
        instances_column.setCellValueFactory(new PropertyValueFactory<>("instances"));

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
                    handleEditAction(duty);
                });

                deleteButton.getStyleClass().add("deleteBtn");
                deleteButton.setOnAction(e -> {
                    Duty duty = getTableView().getItems().get(getIndex());
                    handleDeleteAction(duty);
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

        actions_column.setCellFactory(cellFactory);
    }
    // Reference: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view - END

    /**
     * Function to handle delete all duties
     */
    private void handleDeleteAllAction() {
        duties_table.getItems().clear();
        dutyService.removeAllDuties();
        staffService.removeAllStaffMembers();
    }

    /**
     * Function to handle edit action for duty
     *
     * @param duty the staff member's duty
     */
    private void handleEditAction(Duty duty) {
        ViewFactoryModel.getInstance().getViewFactory().setDuty(duty);
        ViewFactoryModel.getInstance().getViewFactory().getSelectedMenuItem().set("EditDuty");
    }

    /**
     * Function to handle delete button click for duty
     * @param duty the staff member duty
     */
    private void handleDeleteAction(Duty duty) {
        duties_table.getItems().remove(duty);
        dutyService.removeDuty(duty.getStaffId(), duty);
        staffService.updateTotalWorkload(duty.getStaffId(), staffService.getStaffMember(duty.getStaffId()).getTotalWorkload() - duty.getWorkloadForDuty());
    }

    /**
     * Function to search for staff members based on id or name
     */
    private void searchQuery() {
        String query = search_bar.getText().toLowerCase();
        duties_table.getItems().clear();
        if (!query.isEmpty()) {
            duties_table.getItems().clear();
            for (Duty duty : duties.values()) {
                if (String.valueOf(duty.getId()).contains(query) || duty.getDutyType().toLowerCase().contains(query)) {
                    duties_table.getItems().add(duty);
                }
            }
        } else {
            duties_table.getItems().addAll(duties.values());
        }
    }

}
