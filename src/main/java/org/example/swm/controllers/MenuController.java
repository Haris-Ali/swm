package org.example.swm.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.swm.controllers.staff.AddStaffMember;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public Button addSM_button;
    public Button viewSM_button;
    public Button editSM_button;
    public Button deleteSM_button;
    public Button addDuty_button;
    public Button viewDuty_button;
    public Button editDuty_button;
    public Button deleteDuty_button;
    public Button viewWorkload_button;
    public Button logout_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSM_button.setOnAction(e -> {
            AddStaffMember addStaffMember = new AddStaffMember();
            addStaffMember.setup();
        });
    }
}
