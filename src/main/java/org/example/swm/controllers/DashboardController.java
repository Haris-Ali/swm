package org.example.swm.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.example.swm.models.ViewFactoryModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public BorderPane dashboard_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewFactoryModel
                .getInstance()
                .getViewFactory()
                .getSelectedMenuItem()
                .addListener((observableValue, prev, next) -> {
                    switch (next) {
                        case "ViewStaffMembers" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getViewStaffMemberView());
                        case "EditStaffMember" -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getEditStaffMemberView());
                        default -> dashboard_parent.setCenter(ViewFactoryModel.getInstance().getViewFactory().getAddStaffMemberView());
                    }
                });
    }
}
