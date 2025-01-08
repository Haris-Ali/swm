package org.example.swm.controllers.duty;

import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;
import org.example.swm.services.DutyService;
import org.example.swm.services.StaffService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The type Add duty.
 */
public class AddDuty {
    /**
     * The Duty service.
     */
    DutyService dutyService = new DutyService();
    /**
     * The Staff service.
     */
    StaffService staffService = new StaffService();
    /**
     * The scanner object instance.
     */
    Scanner sc = new Scanner(System.in);
    /**
     * The Staff member.
     */
    StaffMember staffMember;
    /**
     * The Selected duty type.
     */
    DutyType selectedDutyType;
    /**
     * The Selected activity type.
     */
    String selectedActivityType;
    /**
     * The Description.
     */
    String description;
    /**
     * The Selected weeks.
     */
    String selectedWeeks;;
    /**
     * The Duration.
     */
    int duration;
    /**
     * The Instances.
     */
    int instances;

    private static final HashMap<DutyType, List<String>> activityTypeOptions = new HashMap<>(); // Hashmap to store activity type options
    //<-***** https://stackoverflow.com/questions/6881224/is-there-a-better-alternative-to-listt-initalization-than-invoking-arrays-asli  - START
    static {
        activityTypeOptions.put(DutyType.ATSR, Arrays.asList("Formal Scheduled Teaching", "Fully Online Delivery", "Dissertation/Projects",
                "Placements/WBL", "PhD Supervision", "DBA Supervision", "Other"));
        activityTypeOptions.put(DutyType.TLR, Arrays.asList("Programme Leader", "Module Leader", "Head of Subject", "Deputy Head of Subject",
                "Associate Professor", "Professor", "Reader", "T&R Contract", "Partnership Manager", "Faculty Equality & Student Inclusion",
                "Faculty Academic Integrity Officer", "Faculty Academic Integrity Support", "Additional Projects, Roles & Similar- Faculty Leads",
                "Research, Innovation & Similar", "Probation", "Partner Programme Liaison", "Partner Module Liaison & Moderation", "Personal  Tutor",
                "Learner Support/assessment support", "Dean's Discretion", "Other"));
        activityTypeOptions.put(DutyType.SA, Arrays.asList("Scholarly, Currency & Development", "Knowledge exchange", "Writing books/journals/conference",
                "Research", "Exhibitions", "Consultancy", "Developing teaching skills", "Community & Public engagement",
                "Liaison with Proff bodies", "CPD", "Other"));
        activityTypeOptions.put(DutyType.Other, Arrays.asList("Other duties - specify", "PSR", "Validations", "MCoA", "Panel Members", "EDI Network Lead",
                "Internal Tier 1 committee", "Faculty Meeting", "Open day- time of in lieu", "Other external meetings",
                "Schools/UTC/Colleges liaison", "Exam Boards"));
    }
    //<-***** https://stackoverflow.com/questions/6881224/is-there-a-better-alternative-to-listt-initalization-than-invoking-arrays-asli  - END

    private static final List<String> weekOptions = Arrays.asList(
            "Trimester 1", "Trimester 2", "Trimester 3", "All Year"
    );

    /**
     * Setup/entry method
     */
    public void setup() {
        inputStaffMemberId();
    }

    /**
     * Method to take input for staff member id
     * Checks for existence before calling other methods for creating new duty
     */
    private void inputStaffMemberId() {
        int staffId = -1;
        staffId = takeInputForStaffMember(staffId, sc);
        staffMember = staffService.getStaffMember(staffId);
        if (staffMember == null) {
            System.out.println("No staff member with this id found");
            return;
        }
        addDutyType();
        addDutyActivityType();
        addDescription();
        addWeeks();
        addDuration();
        addInstances();
        addDuty(staffId);
    }

    private void addDutyType() {
        System.out.println("Select a Duty Type:");
        int index = 1;
        for (DutyType type : DutyType.values()) {
            System.out.println(index++ + ". " + type);
        }
        System.out.print("Enter your choice: ");
        int dutyChoice = sc.nextInt();
        sc.nextLine();

        try {
            selectedDutyType = DutyType.values()[dutyChoice - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid duty type selected.");
        }
    }

    /**
     * Method to take input for duty activity type
     */
    private void addDutyActivityType() {
        List<String> activities = activityTypeOptions.get(selectedDutyType);
        System.out.println("Select an Activity Type for " + selectedDutyType + ":");
        for (int i = 0; i < activities.size(); i++) {
            System.out.println((i + 1) + ". " + activities.get(i));
        }

        System.out.print("Enter your choice: ");
        int activityChoice = sc.nextInt();
        sc.nextLine();

        try {
            selectedActivityType = activities.get(activityChoice - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid activity type selected.");
        }
    }

    /**
     * Method to take input for duty description
     */
    private void addDescription() {
        System.out.print("Enter description: ");
        description = sc.nextLine();
    }

    /**
     * Method to take input for duty weeks (trimester 1/2/3 or all year)
     */
    private void addWeeks() {
        System.out.println("Select the Weeks:");
        for (int i = 0; i < weekOptions.size(); i++) {
            System.out.println((i + 1) + ". " + weekOptions.get(i));
        }

        System.out.print("Enter your choice: ");
        int weekChoice = sc.nextInt();
        sc.nextLine();

        try {
            selectedWeeks = weekOptions.get(weekChoice - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid weeks selection.");
        }
    }

    /**
     * Method to take input for duty duration in hours
     */
    private void addDuration() {
        System.out.print("Enter duration (hours per week): ");
        duration = sc.nextInt();
    }

    /**
     * Method to take input for duty instances
     */
    private void addInstances() {
        System.out.print("Enter instances: ");
        instances = sc.nextInt();
    }

    /**
     * Method that creates new duty object, calculate hour categories based on duration and instances.
     * First check for workload being less than 1570 before adding duty to staff member and updating files.
     * @param staffId the staff member id
     */
    private void addDuty(int staffId) {
        Duty duty = new Duty(staffId, selectedDutyType.name(), selectedActivityType, description, selectedWeeks, duration, instances);
        duty.calculateHourCategories();
        int workload = duty.getWorkloadForDuty();
        int totalWorkload = staffMember.getTotalWorkload();
        if (totalWorkload + workload > 1570) {
            System.out.println("Total workload exceeding the maximum limit of 1570. Please enter appropriate values for duration and instances");
            return;
        }
        staffService.updateTotalWorkload(staffId, totalWorkload + workload);
        dutyService.addDutyAgainstStaffMember(staffId, duty);
    }

    /**
     * Take input for staff member int.
     *
     * @param staffId the staff id
     * @param sc      the scanner object instance
     * @return the input for staff member id
     */
    public int takeInputForStaffMember(int staffId, Scanner sc) {
        while (staffId == -1) {
            System.out.print("Enter staff ID: ");
            try {
                staffId = Integer.parseInt(sc.nextLine());
                if (staffId > 0) {
                    break;
                } else {
                    System.out.println("Invalid input. Staff Ids can only be positive numbers");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number");
            }
        }
        return staffId;
    }

}
