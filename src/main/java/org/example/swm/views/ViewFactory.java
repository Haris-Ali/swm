package org.example.swm.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.swm.controllers.DashboardController;
import org.example.swm.models.StaffMember;

public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private StaffMember staffMember;
    private AnchorPane addStaffMemberView;
    private AnchorPane viewStaffMemberView;
    private AnchorPane editStaffMemberView;

    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public void setStaffMember(StaffMember staffMember) {
        System.out.println("Updating staffMember in ViewFactory: " + staffMember.getName());
        this.staffMember = staffMember;
    }

    public StaffMember getStaffMember() {
        return staffMember;
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

    public AnchorPane getEditStaffMemberView() {
        try {
            editStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/EditStaffMember.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editStaffMemberView;
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
