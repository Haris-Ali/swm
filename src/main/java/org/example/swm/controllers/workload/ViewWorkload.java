package org.example.swm.controllers.workload;

import org.example.swm.models.Duty;
import org.example.swm.models.HourCategory;
import org.example.swm.models.StaffMember;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The class ViewWorkload.
 */
public class ViewWorkload {
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
     * The Duties.
     */
    HashMap<Integer, Duty> duties = new HashMap<>();
    /**
     * The Scanner object instance
     */
    Scanner sc = new Scanner(System.in);

    /**
     * Read staff members from file and checks for existence
     */
    public void setup() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) System.out.println("No staff members found hence no duties exist yet");
        else getInput();
    }

    /**
     * Take input for displaying all staff member workload or specific staff member workload
     */
    private void getInput() {
        System.out.print("Display workload for all staff members (y/N) (any other input to go back: ");
        String userInput = sc.nextLine();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            displayWorkloadForAllStaffMembers();
        } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
            displayWorkloadForSingleStaffMember();
        } else {
            System.out.println("Action terminated. Going back");
        }
    }

    /**
     * Take staff member id as input and check for existence.
     * Get duties of staff member
     */
    private void displayWorkloadForSingleStaffMember() {
        System.out.print("Enter staff member id: ");
        int staffId = sc.nextInt();
        sc.nextLine();

        if (!staffMembers.containsKey(staffId)) {
            System.out.println("Staff member does not exist");
            return;
        }
        StaffMember staffMember = staffService.getStaffMember(staffId);
        duties = dutyService.getDutiesAgainstStaffMember(staffId);
        displayWorkload(staffMember, duties);
    }

    /**
     * Get all workload of all staff
     */
    private void displayWorkloadForAllStaffMembers() {
        for (StaffMember staffMember : staffMembers.values()) {
            HashMap<Integer, Duty> duties = dutyService.getDutiesAgainstStaffMember(staffMember.getId());
            displayWorkload(staffMember, duties);
        }
    }

    /**
     * Display the workload for provided staff member and their duties
     * @param staffMember the staff member object
     * @param duties map of staff member's duties
     */
    private void displayWorkload(StaffMember staffMember, HashMap<Integer, Duty> duties) {
        System.out.println("Staff Member Details: ");
        System.out.println("-------------------------");
        System.out.println("ID: " + staffMember.getId());
        System.out.println("Name: " + staffMember.getName());
        System.out.println("Contract Type: " + staffMember.getContractType());
        System.out.println("Subject Area: " + staffMember.getSubjectArea());
        System.out.println("Line Manager: " + staffMember.getLineManager());
        System.out.println();

        System.out.println("Staff Member Duties: ");
        System.out.println("-------------------------");
        for (Duty duty : duties.values()) {
            System.out.println("Duty ID: " + duty.getId());
            System.out.println("Duty Type: " + duty.getDutyType());
            System.out.println("Activity Type: " + duty.getActivityType());
            System.out.println("Description: " + duty.getDescription());
            System.out.println("Weeks: " + duty.getWeeks());
            System.out.println("Duration: " + duty.getDuration());
            System.out.println("Instances: " + duty.getInstances());
            System.out.println("Hours: " + duty.getHours());

            // Sum Hour Categories
            int totalCategoryHours = 0;
            System.out.println("Hour Categories:");
            for (HourCategory hourCategory : duty.getHourCategories()) {
                System.out.println("    - " + hourCategory.getName() + ": " + hourCategory.getHours() + " hours");
                totalCategoryHours += hourCategory.getHours();
            }
            System.out.println("Duty Workload Hours: " + totalCategoryHours);
            System.out.println("-------------------------");
        }
        System.out.println("Total Workload: " + staffMember.getTotalWorkload());
    }
}
