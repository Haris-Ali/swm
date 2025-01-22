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

    /**
     * Instantiates a new View factory.
     */
    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    /**
     * Gets selected menu item.
     *
     * @return the selected menu item
     */
    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    /**
     * Sets staff member.
     *
     * @param staffMember the staff member
     */
    public void setStaffMember(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    /**
     * Gets staff member.
     *
     * @return the staff member
     */
    public StaffMember getStaffMember() {
        return this.staffMember;
    }

    /**
     * Sets duty.
     *
     * @param duty the duty
     */
    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    /**
     * Gets duty.
     *
     * @return the duty
     */
    public Duty getDuty() { return this.duty; }

    /**
     * Gets home view.
     *
     * @return the home view
     */
    public AnchorPane getHomeView() {
        try {
            homeView = new FXMLLoader(getClass().getResource("/fxml/Home.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return homeView;
    }

    /**
     * Gets add staff member view.
     *
     * @return the add staff member view
     */
    public AnchorPane getAddStaffMemberView() {
        try {
            addStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/AddStaffMember.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addStaffMemberView;
    }

    /**
     * Gets view staff member view.
     *
     * @return the view staff member view
     */
    public AnchorPane getViewStaffMemberView() {
        try {
            viewStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/ViewStaffMembers.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewStaffMemberView;
    }

    /**
     * Gets edit staff member view.
     *
     * @return the edit staff member view
     */
    public AnchorPane getEditStaffMemberView() {
        try {
            editStaffMemberView = new FXMLLoader(getClass().getResource("/fxml/staff/EditStaffMember.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editStaffMemberView;
    }

    /**
     * Gets view staff member details view.
     *
     * @return the view staff member details view
     */
    public AnchorPane getViewStaffMemberDetailsView() {
        try {
            viewStaffMemberDetailsView = new FXMLLoader(getClass().getResource("/fxml/staff/ViewStaffMember.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewStaffMemberDetailsView;
    }

    /**
     * Gets add duty view.
     *
     * @return the add duty view
     */
    public AnchorPane getAddDutyView() {
        try {
            addDutyView = new FXMLLoader(getClass().getResource("/fxml/duty/AddDuty.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addDutyView;
    }

    /**
     * Gets edit duty view.
     *
     * @return the edit duty view
     */
    public AnchorPane getEditDutyView() {
        try {
            editDutyView = new FXMLLoader(getClass().getResource("/fxml/duty/EditDuty.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editDutyView;
    }

    /**
     * Gets view duties view.
     *
     * @return the view duties view
     */
    public AnchorPane getViewDutiesView() {
        try {
            viewDutyDetailsView = new FXMLLoader(getClass().getResource("/fxml/duty/ViewDuties.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewDutyDetailsView;
    }

    /**
     * Show login window.
     */
    public void showLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        createStage(fxmlLoader);
    }

    /**
     * Show dashboard window.
     */
    public void showDashboardWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
        DashboardController dashboardController = new DashboardController();
        fxmlLoader.setController(dashboardController);
        createStage(fxmlLoader);
    }

    /**
     * Create stage.
     *
     * @param fxmlLoader the fxml loader
     */
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

    /**
     * Close stage.
     *
     * @param stage the stage
     */
    public void closeStage(Stage stage) {
        stage.close();
    }
}
