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
 * The class Edit duty.
 */
public class EditDuty {
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
     * The Staff id.
     */
    int staffId = -1;
    /**
     * The Duty id.
     */
    int dutyId = -1;

    /**
     * The Duty.
     */
    Duty duty;

    private static final HashMap<DutyType, List<String>> activityTypeOptions = new HashMap<>();
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
     * Read staff members from file. Check for their existence.
     */
    public void setup() {
        staffMembers = staffService.readFromFile();
        if (staffMembers.isEmpty()) System.out.println("No staff members found");
        else getInput();
    }

    /**
     * Method to take input for staff member id and duty id
     * Checks for existence before calling other methods for updating duty
     */
    private void getInput() {
        System.out.print("Enter the ID of the staff member to edit: ");
        staffId = sc.nextInt();
        sc.nextLine();

        if (!staffMembers.containsKey(staffId)) {
            System.out.println("Staff member does not exist");
            return;
        }

        duties = dutyService.readFromFile("data/duties/duties_staff_" + staffId + ".dat", staffId);
        System.out.print("Enter the ID of the duty to edit: ");
        dutyId = sc.nextInt();
        sc.nextLine();

        if (!duties.containsKey(dutyId)) {
            System.out.println("Duty does not exist");
            return;
        }
        duty = dutyService.getDutyAgainstStaffMember(staffId, dutyId);
        System.out.println("Editing details for following duty: ");
        System.out.println("Duty type: " + duty.getDutyType());
        System.out.println("Duty activity: " + duty.getActivityType());
        System.out.println("Duty description: " + duty.getDescription());
        editDutyType();
        editDutyActivityType();
        editDescription();
        editWeeks();
        editDuration();
        editInstances();
        editDuty();
    }

    /**
     * Method to take input for updating duty type
     */
    private void editDutyType() {
        System.out.println("Select a new Duty Type (leave blank to keep \"" + duty.getDutyType() + "\"):");
        int index = 1;
        for (DutyType type : DutyType.values()) {
            System.out.println(index++ + ". " + type);
        }
        System.out.print("Enter your choice: ");
        String dutyChoice = sc.nextLine();
        if (!dutyChoice.isBlank()) {
            try {
                int selectedIndex = Integer.parseInt(dutyChoice);
                duty.setDutyType(DutyType.values()[selectedIndex - 1].name());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid duty type selection. Keeping existing value.");
            }
        }
    }

    /**
     * Method to take input for updating duty activity type
     */
    private void editDutyActivityType() {
        List<String> activities = activityTypeOptions.get(DutyType.valueOf(duty.getDutyType()));
        System.out.println("Select a new Activity Type for " + duty.getDutyType() + " (leave blank to keep \"" + duty.getActivityType() + "\"):");
        for (int i = 0; i < activities.size(); i++) {
            System.out.println((i + 1) + ". " + activities.get(i));
        }
        System.out.print("Enter your choice: ");
        String activityChoice = sc.nextLine();
        if (!activityChoice.isBlank()) {
            try {
                int selectedIndex = Integer.parseInt(activityChoice);
                duty.setActivityType(activities.get(selectedIndex - 1));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Invalid activity type selection. Keeping existing value.");
            }
        }
    }

    /**
     * Method to take input for updating duty description
     */
    private void editDescription() {
        System.out.print("Enter new description (leave blank to keep \"" + duty.getDescription() + "\"): ");
        String description = sc.nextLine();
        if (!description.isBlank()) {
            duty.setDescription(description);
        }
    }

    /**
     * Method to take input for updating duty weeks
     */
    private void editWeeks() {
        System.out.println("Select new Weeks (leave blank to keep \"" + duty.getWeeks() + "\"):");
        for (int i = 0; i < weekOptions.size(); i++) {
            System.out.println((i + 1) + ". " + weekOptions.get(i));
        }
        System.out.print("Enter your choice: ");
        String weekChoice = sc.nextLine();
        if (!weekChoice.isBlank()) {
            try {
                int selectedIndex = Integer.parseInt(weekChoice);
                duty.setWeeks(weekOptions.get(selectedIndex - 1));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Invalid weeks selection. Keeping existing value.");
            }
        }
    }

    /**
     * Method to take input for updating duty duration in hours
     */
    private void editDuration() {
        System.out.print("Enter new duration (hours per week, leave blank to keep \"" + duty.getDuration() + "\"): ");
        String durationInput = sc.nextLine();
        if (!durationInput.isBlank()) {
            try {
                duty.setDuration(Integer.parseInt(durationInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping existing value.");
            }
        }
    }

    /**
     * Method to take input for updating duty instances
     */
    private void editInstances() {
        System.out.print("Enter new instances (leave blank to keep \"" + duty.getInstances() + "\"): ");
        String instancesInput = sc.nextLine();
        if (!instancesInput.isBlank()) {
            try {
                duty.setInstances(Integer.parseInt(instancesInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping existing value.");
            }
        }
    }

    /**
     * Method that updates duty object, calculate hour categories based on duration and instances.
     * First check for workload being less than 1570 before updating duty to staff member and updating files.
     */
    private void editDuty() {
        int prevWorkload = duty.getWorkloadForDuty();
        duty.setHours();
        duty.calculateHourCategories();
        int newWorkload = duty.getWorkloadForDuty();
        int totalWorkload = staffService.getStaffMember(staffId).getTotalWorkload();
        if ((totalWorkload - (prevWorkload - newWorkload)) + newWorkload > 1570) {
            System.out.println("Total workload exceeding the maximum limit of 1570. Please enter appropriate values for duration and instances");
            return;
        }
        staffService.updateTotalWorkload(staffId, totalWorkload - (prevWorkload - newWorkload));
        dutyService.addDutyAgainstStaffMember(staffId, duty);
    }
}
