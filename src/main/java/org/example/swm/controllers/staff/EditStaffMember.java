package org.example.swm.controllers.staff;

import org.example.swm.models.StaffMember;
import org.example.swm.services.StaffService;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The class Edit staff member.
 */
public class EditStaffMember {
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Staff members.
     */
    HashMap<Integer, StaffMember> staffMembers;
    /**
     * The Scanner object instance
     */
    Scanner sc = new Scanner(System.in);

    /**
     * Read staff members from file and check for existence
     */
    public void setup() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) System.out.println("No staff members found");
        else getInput();
    }

    /**
     * Input staff member id or name and check for existence
     */
    public void getInput() {
        System.out.print("Enter the ID of the staff member to edit: ");
        int staffId = sc.nextInt();
        sc.nextLine();

        if (!staffMembers.containsKey(staffId)) {
            System.out.println("Staff member does not exist");
            return;
        }
        editStaffMember(staffId);
    }

    /**
     * Edit staff member.
     *
     * @param staffId the staff id
     */
    public void editStaffMember(int staffId) {
        StaffMember staffMember = staffMembers.get(staffId);
        System.out.println("Editing details for staff member: " + staffMember.getName());

        System.out.print("Enter new name (leave blank to keep \"" + staffMember.getName() + "\"): ");
        String name = sc.nextLine();
        if (!name.isBlank()) staffMember.setName(name);

        Double staffContractType = null;
        while (staffContractType == null) {
            System.out.print("Enter new contract type? (1.0/0.5 (full time/part time), leave blank to keep \"" + staffMember.getContractType() + "\"): ");
            String ctInput = sc.nextLine();
            if (!ctInput.isBlank()) {
                if (ctInput.matches("^(1(\\.0)?|0\\.5|\\.5)$")) {
                    staffContractType = Double.parseDouble(ctInput);
                } else {
                    System.out.println("Invalid input. Please enter 1, 1.0, 0.5, or .5");
                }
            } else {
                staffContractType = staffMember.getContractType();
            }
            staffMember.setContractType(staffContractType);
        }

        System.out.print("Enter new subject area (leave blank to keep \"" + staffMember.getSubjectArea() + "\"): ");
        String subjectArea = sc.nextLine();
        if (!subjectArea.isBlank()) {
            staffMember.setSubjectArea(subjectArea);
        }

        System.out.print("Enter new line manager (leave blank to keep \"" + staffMember.getLineManager() + "\"): ");
        String lineManager = sc.nextLine();
        if (!lineManager.isBlank()) {
            staffMember.setLineManager(lineManager);
        }

        staffService.addStaffMember(staffMember);
        System.out.println("Staff member details updated successfully.");
    }
}
