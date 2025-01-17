package org.example.swm.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.swm.controllers.DashboardController;

public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private AnchorPane addStaffMemberView;
    private AnchorPane viewStaffMemberView;

    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public AnchorPane getAddStaffMemberView() {
        if (addStaffMemberView == null) {
            try {
                addStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/AddStaffMember.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addStaffMemberView;
    }

    public AnchorPane getViewStaffMemberView() {
        if (viewStaffMemberView == null) {
            try {
                viewStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/ViewStaffMembers.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return viewStaffMemberView;
    }

    public void showLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        createStage(fxmlLoader);
    }

    public void showDashboardWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
        DashboardController dashboardController = new DashboardController();
        fxmlLoader.setController(dashboardController);
        createStage(fxmlLoader);
    }

    public void createStage(FXMLLoader fxmlLoader) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Staff Workload Management");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
