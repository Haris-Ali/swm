package org.example.swm.controllers.staff;

import org.example.swm.models.StaffMember;
import org.example.swm.services.StaffService;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The class View staff members.
 */
public class ViewStaffMembers {
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The Staff members.
     */
    HashMap<Integer, StaffMember> staffMembers = new HashMap<>();
    /**
     * The Scanner object instance.
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
     * Input staff member id and check for existence
     */
    public void getInput() {
        System.out.print("Display all staff members (y/N): ");
        String userInput = sc.nextLine();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            displayAllStaffMembers();
        } else {
            displaySingleStaffMember();
        }
    }

    /**
     * Search for staff member by id or name.
     * Display single staff member.
     */
    public void displaySingleStaffMember() {
        System.out.print("Search for staff member by id or name: ");
        String userInput = sc.nextLine();
        for (StaffMember staffMember : staffMembers.values()) {
            if (staffMember.getId() == Integer.parseInt(userInput) || staffMember.getName().equalsIgnoreCase(userInput)) {
                System.out.println(staffMember);
            } else {
                System.out.println("Staff member not found");
                getInput();
            }
        }
    }

    /**
     * Display all staff members.
     */
    public void displayAllStaffMembers() {
        System.out.println("All Staff Members: ");
        for (StaffMember staffMember : staffMembers.values()) {
            System.out.println(staffMember);
        }
    }
}
