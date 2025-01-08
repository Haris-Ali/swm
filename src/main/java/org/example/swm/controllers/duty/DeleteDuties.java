package org.example.swm.controllers.duty;

import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The class Delete duties.
 */
public class DeleteDuties {
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
     * The Duties.
     */
    HashMap<Integer, Duty> duties;
    /**
     * The Scanner object instance
     */
    Scanner sc = new Scanner(System.in);

    /**
     * Setup/entry method
     * Read staff members from file and check for existence
     */
    public void setup() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) System.out.println("No staff members found");
        else getInput();
    }

    /**
     * Take input for deleting all or specific duty.
     */
    private void getInput() {
        System.out.print("Delete duties of all staff members (y/N) (any other input to go back): ");
        String userInput = sc.nextLine();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            deleteDutiesForAllStaffMembers();
        } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
            singleStaffMember();
        } else {
            System.out.println("Action terminated. Going back");
        }
    }

    /**
     * Take input of staff member id. Check for id existence.
     * Take input for deleting all or specific duties of this staff member
     */
    private void singleStaffMember() {
        System.out.print("Enter the ID of the staff member to delete: ");
        int staffId = sc.nextInt();
        sc.nextLine();

        if (!staffMembers.containsKey(staffId)) {
            System.out.println("Staff member does not exist");
            return;
        }
        System.out.print("Delete all duties of this staff member (y/N) (any other input to go back): ");
        String userInput = sc.nextLine();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            deleteAllDuties(staffId);
        } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
            deleteSingleDuty(staffId);
        } else {
            System.out.println("Action terminated. Going back");
        }
    }

    /**
     * Gets duties of staff member from file
     * Inputs the duty id from user, checks for existence before deleting duty
     * @param staffId the staff member id
     */
    private void deleteSingleDuty(int staffId) {
        duties = dutyService.readFromFile("data/duties/duties_staff_" + staffId + ".dat", staffId);
        System.out.print("Enter the ID of the duty to delete: ");
        int dutyId = sc.nextInt();
        sc.nextLine();
        if (!duties.containsKey(dutyId)) {
            System.out.println("Duty with this id does not exist");
            return;
        }
        dutyService.removeDuty(staffId, duties.get(dutyId));
    }

    /**
     * Deletes all duties specific to the staff member
     *
     * @param staffId the staff memebr id
     */
    private void deleteAllDuties(int staffId) {
        dutyService.removeAllDutiesAgainstStaffMember(staffId);
    }

    /**
     * Delete all duties
     */
    private void deleteDutiesForAllStaffMembers() {
        dutyService.removeAllDuties();
    }
}
