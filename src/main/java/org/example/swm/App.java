package org.example.swm;

import org.example.swm.controllers.duty.AddDuty;
import org.example.swm.controllers.duty.DeleteDuties;
import org.example.swm.controllers.duty.EditDuty;
import org.example.swm.controllers.duty.ViewDuties;
import org.example.swm.controllers.staff.AddStaffMember;
import org.example.swm.controllers.staff.DeleteStaffMembers;
import org.example.swm.controllers.staff.EditStaffMember;
import org.example.swm.controllers.staff.ViewStaffMembers;
import org.example.swm.controllers.workload.ViewWorkload;
import org.example.swm.services.PasswordService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The class App serving as entry point.
 */
public class App {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        login();
        handleChoice();
    }

    /**
     * Method to login user via username and password
     */
    private static void login() {
        Scanner sc = new Scanner(System.in);
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            try {
                if (PasswordService.verifyUser(username, password)) {
                    System.out.println("Login successful!");
                    isLoggedIn = true;
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
            } catch (IOException | NoSuchAlgorithmException e) {
                System.out.println("An error occurred during login: " + e.getMessage());
            }
        }
    }

    /**
     * Method to print the options on CLI
     */
    private static void setup() {
        System.out.println("_______________________________________");
        System.out.println("|                                     |");
        System.out.println("|              DASHBOARD              |");
        System.out.println("|                                     |");
        System.out.println("|     Please choose an option:        |");
        System.out.println("|                                     |");
        System.out.println("|     1. Add Staff                    |");
        System.out.println("|     2. Add Duty                     |");
        System.out.println("|     3. Edit Staff                   |");
        System.out.println("|     4. Edit Duty                    |");
        System.out.println("|     5. View Staff                   |");
        System.out.println("|     6. View Duties                  |");
        System.out.println("|     7. View Workload                |");
        System.out.println("|     8. Delete Staff                 |");
        System.out.println("|     9. Delete Duty                  |");
        System.out.println("|     0. Exit                         |");
        System.out.println("|_____________________________________|");
        System.out.print("Enter your choice: ");
    }

    /**
     * Method to take input for choice which is then handled with a switch statement
     */
    private static void handleChoice() {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            setup();
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please only enter a value from 0 to 9.");
                continue;
            }

            switch (choice) {
                case 1:
                    AddStaffMember addStaffMember = new AddStaffMember();
                    addStaffMember.setup();
                    break;
                case 2:
                    AddDuty addDuty = new AddDuty();
                    addDuty.setup();
                    break;
                case 3:
                    EditStaffMember editStaffMember = new EditStaffMember();
                    editStaffMember.setup();
                    break;
                case 4:
                    EditDuty editDuty = new EditDuty();
                    editDuty.setup();
                    break;
                case 5:
                    ViewStaffMembers viewStaffMembers = new ViewStaffMembers();
                    viewStaffMembers.setup();
                    break;
                case 6:
                    ViewDuties viewDuties = new ViewDuties();
                    viewDuties.setup();
                    break;
                case 7:
                    ViewWorkload viewWorkload = new ViewWorkload();
                    viewWorkload.setup();
                    break;
                case 8:
                    DeleteStaffMembers deleteStaffMembers = new DeleteStaffMembers();
                    deleteStaffMembers.setup();
                    break;
                case 9:
                    DeleteDuties deleteDuties = new DeleteDuties();
                    deleteDuties.setup();
                    break;
                case 0:
                    System.out.println("Exiting Application");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    setup();
            }
        }
    }
}
