package org.example.swm.controllers.staff;

import org.example.swm.models.StaffMember;
import org.example.swm.services.StaffService;

import java.util.Scanner;

/**
 * The class Add staff member for adding new staff members.
 */
public class AddStaffMember {
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();

    /**
     * Setup function to input staff name, contract type, subject area & line manager
     */
    public void setup() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Staff Name: ");
        String staffName = sc.nextLine();
        Double staffContractType = null;
        while (staffContractType == null) {
            System.out.print("Enter Staff Contract Type (1.0 for full time & 0.5 for part-time): ");
            String ctInput = sc.nextLine();
            if (ctInput.matches("^(1(\\.0)?|0\\.5|\\.5)$")) {
                staffContractType = Double.parseDouble(ctInput);
            } else {
                System.out.println("Invalid input. Please enter 1, 1.0, 0.5, or .5");
            }
        }
        System.out.print("Enter Subject Area: ");
        String staffSubjectArea = sc.nextLine();
        System.out.print("Enter Line Manager: ");
        String staffLineManager = sc.nextLine();

        addStaffMember(staffName, staffContractType, staffSubjectArea, staffLineManager);
    }

    /**
     * Method to add new staff member
     * @param name the staff member name
     * @param contractType the contract type (full-time/part-time)
     * @param subjectArea the subject area
     * @param lineManager the line manager
     */
    private void addStaffMember(String name, Double contractType, String subjectArea, String lineManager) {
        StaffMember sm = new StaffMember(name, contractType, subjectArea, lineManager);
        staffService.addStaffMember(sm);
    }
}
