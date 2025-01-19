package org.example.swm.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.swm.controllers.DashboardController;
import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;

import java.io.IOException;

public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private StaffMember staffMember;
    private Duty duty;
    private AnchorPane homeView;
    private AnchorPane addStaffMemberView;
    private AnchorPane viewStaffMemberView;
    private AnchorPane editStaffMemberView;
    private AnchorPane viewStaffMemberDetailsView;
    private AnchorPane addDutyView;
    private AnchorPane editDutyView;
    private AnchorPane viewDutyDetailsView;

    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public void setStaffMember(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public StaffMember getStaffMember() {
        return this.staffMember;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public Duty getDuty() { return this.duty; }

    public AnchorPane getHomeView() {
        try {
            homeView = new FXMLLoader(getClass().getResource("/fxml/Home.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return homeView;
    }

    public AnchorPane getAddStaffMemberView() {
        try {
            addStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/AddStaffMember.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addStaffMemberView;
    }

    public AnchorPane getViewStaffMemberView() {
        try {
            viewStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/ViewStaffMembers.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
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

    public AnchorPane getViewStaffMemberDetailsView() {
        try {
            viewStaffMemberDetailsView = new FXMLLoader(getClass().getResource("/fxml/staff/ViewStaffMember.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewStaffMemberDetailsView;
    }

    public AnchorPane getAddDutyView() {
        try {
            addDutyView = new FXMLLoader(getClass().getResource("/fxml/duty/AddDuty.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addDutyView;
    }

    public AnchorPane getEditDutyView() {
        try {
            editDutyView = new FXMLLoader(getClass().getResource("/fxml/duty/EditDuty.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editDutyView;
    }

    public AnchorPane getViewDutiesView() {
        try {
            viewDutyDetailsView = new FXMLLoader(getClass().getResource("/fxml/duty/ViewDuties.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewDutyDetailsView;
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
