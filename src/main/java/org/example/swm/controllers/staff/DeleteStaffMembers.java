package org.example.swm.controllers.staff;

import org.example.swm.models.StaffMember;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The class Delete staff members.
 */
public class DeleteStaffMembers {
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
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
     * Gets input to delete all or specific staff member
     */
    public void getInput() {
        System.out.print("Delete all staff members (y/N) (any other input to go back): ");
        String userInput = sc.nextLine();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            deleteAllStaffMembers();
        } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
            deleteSingleStaffMember();
        } else {
            System.out.println("Action terminated. Going back");
        }
    }

    /**
     * Delete single staff member and associated duties.
     */
    public void deleteSingleStaffMember() {
        System.out.println("Please be aware that any duties linked to staff members will also be deleted. This action is non-reversible.");
        System.out.print("Enter the ID of the staff member to delete: ");
        int staffId = sc.nextInt();
        sc.nextLine();

        if (!staffMembers.containsKey(staffId)) {
            System.out.println("Staff member does not exist");
            return;
        }
        dutyService.removeAllDutiesAgainstStaffMember(staffId);
        staffService.removeStaffMember(staffId);
    }

    /**
     * Delete all staff members and associated duties.
     */
    public void deleteAllStaffMembers() {
        System.out.println("Please be aware that any duties linked to the staff members will also be deleted. This action is non-reversible.");
        dutyService.removeAllDuties();
        staffService.removeAllStaffMembers();
    }
}
