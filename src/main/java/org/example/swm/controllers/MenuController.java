package org.example.swm.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.swm.models.ViewFactoryModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Menu controller.
 */
public class MenuController implements Initializable {
    /**
     * The Add sm button.
     */
    public Button addSM_button;
    /**
     * The View sm button.
     */
    public Button viewSM_button;
    /**
     * The View duty button.
     */
    public Button viewDuty_button;
    /**
     * The View workload button.
     */
    public Button viewWorkload_button;
    /**
     * The Logout button.
     */
    public Button logout_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    /**
     * A function to attach click listeners to menu buttons
     */
    private void addListeners() {
        addSM_button.setOnAction(e -> changeView("AddStaffMember"));
        viewSM_button.setOnAction(e -> changeView("ViewStaffMembers"));
        viewDuty_button.setOnAction(e -> changeView("ViewDuties"));
        logout_button.setOnAction(e -> logout());
    }

    /**
     * Function to handle view change on button click
     * @param viewName the name of the new view
     */
    private void changeView(String viewName) {
        ViewFactoryModel.getInstance().getViewFactory().getSelectedMenuItem().set(viewName);
    }

    private void logout() {
        Stage stage = (Stage) logout_button.getScene().getWindow();
        ViewFactoryModel.getInstance().getViewFactory().closeStage(stage);
        ViewFactoryModel.getInstance().getViewFactory().showLoginWindow();
    }
}
