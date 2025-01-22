package org.example.swm.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.example.swm.models.ViewFactoryModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public BorderPane dashboard_parent;

    /**
     * Function to switch the center view of dashboard border pane based on selected menu item chagne
     * @param url the current url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewFactoryModel
                .getInstance()
                .getViewFactory()
                .getSelectedMenuItem()
                .addListener((observableValue, prev, next) -> {
                    switch (next) {
                        case "AddStaffMember" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getAddStaffMemberView());
                        case "ViewStaffMembers" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getViewStaffMemberView());
                        case "ViewStaffMember" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getViewStaffMemberDetailsView());
                        case "EditStaffMember" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getEditStaffMemberView());
                        case "AddDuty" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getAddDutyView());
                        case "EditDuty" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getEditDutyView());
                        case "ViewDuties" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getViewDutiesView());
                        default -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getHomeView());
                    }
                });
    }
}
