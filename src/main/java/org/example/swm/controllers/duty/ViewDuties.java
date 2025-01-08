package org.example.swm.controllers.duty;

import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The class ViewDuties.
 */
public class ViewDuties {
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Staff members.
     */
    HashMap<Integer, StaffMember> staffMembers = new HashMap<>();
    /**
     * The Scanner object instance
     */
    Scanner sc = new Scanner(System.in);

    /**
     * Reads staff members from file and checks for existence
     */
    public void setup() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) System.out.println("No staff members found hence no duties exist yet");
        else getInput();
    }

    /**
     * Take input for displaying all staff member duties or specific staff member duties
     */
    private void getInput() {
        System.out.print("Display all staff members duties (y/N) (any other input to go back: ");
        String userInput = sc.nextLine();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            displayDutiesForAllStaffMembers();
        } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
            displayDutiesAgainstSingleStaffMember();
        } else {
            System.out.println("Action terminated. Going back");
        }
    }

    /**
     * Take staff member id as input and check for existence.
     * Get duties of staff member
     */
    private void displayDutiesAgainstSingleStaffMember() {
        System.out.print("Enter staff member id: ");
        int staffId = sc.nextInt();
        sc.nextLine();

        if (!staffMembers.containsKey(staffId)) {
            System.out.println("Staff member does not exist");
            return;
        }
        HashMap<Integer, Duty> duties = dutyService.getDutiesAgainstStaffMember(staffId);
        printDuties(duties);
    }

    /**
     * Get all duties
     */
    private void displayDutiesForAllStaffMembers() {
        HashMap<Integer, Duty> duties = dutyService.getAllDuties();
        printDuties(duties);
    }

    /**
     * Method to print all the duties
     * @param duties the hashmap for duties
     */
    private void printDuties(HashMap<Integer, Duty> duties) {
        for (Duty duty : duties.values()) {
            System.out.println(duty);
        }
    }
}
