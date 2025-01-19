package org.example.swm.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Label total_sm_lbl;
    public Label total_duty_lbl;

    private final StaffService staffService = new StaffService();
    private final DutyService dutyService = new DutyService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData();
    }

    private void getData() {
        int staffCount = staffService.getTotalStaffCount();
        int dutiesCount = dutyService.getTotalDutiesCount();
        total_sm_lbl.setText(String.valueOf(staffCount));
        total_duty_lbl.setText(String.valueOf(dutiesCount));
    }
}
