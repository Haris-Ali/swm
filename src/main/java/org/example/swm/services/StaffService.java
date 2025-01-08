package org.example.swm.services;

import org.example.swm.models.StaffMember;

import java.io.*;
import java.util.HashMap;

/**
 * The class Staff service.
 */
public class StaffService {
    private static final String DATA_DIRECTORY_PATH = "data";
    private static final String STAFF_FILE_PATH = "staff.dat"; // Path where staff members data will be stored
    private final HashMap<Integer, StaffMember> staffMembersHashMap; // Hash map for staff members

    /**
     * Instantiates a new Staff service.
     * Reads data from file and saves to hash map
     */
    public StaffService() {
        staffMembersHashMap = readFromFile();
    }

    /**
     * Read from staff file &  write to hash map.
     *
     * @return the hash map
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, StaffMember> readFromFile() {
        HashMap<Integer, StaffMember> map = new HashMap<>();
        dataDirectoryExists();
        staffFileExists();

        File staffFile = new File(DATA_DIRECTORY_PATH, STAFF_FILE_PATH);
        if (!staffFile.exists()) {
            System.out.println("File does not exist: " + staffFile.getPath());
            return map;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(staffFile))) {
            map = (HashMap<Integer, StaffMember>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The following error occurred: " + e.getMessage());
        }
        return map;
    }

    /**
     * Write to file.
     */
    public void writeToFile() {
        dataDirectoryExists();
        staffFileExists();

        File staffFile = new File(DATA_DIRECTORY_PATH, STAFF_FILE_PATH);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(staffFile))) {
            out.writeObject(staffMembersHashMap);
        } catch (IOException e) {
            System.out.println("The following error occurred: " + e.getMessage());
        }
    }

    /**
     * Check and create the data directory
     */
    public void dataDirectoryExists() {
        File dataDirectory = new File(DATA_DIRECTORY_PATH);
        if (!dataDirectory.exists()) {
            if (dataDirectory.mkdir()) {
                System.out.println("Data directory created: " + dataDirectory.getPath());
            } else {
                System.out.println("Failed to create data directory: " + dataDirectory.getPath());
            }
        }
    }

    /**
     * Check and create an empty staff file
     */
    public void staffFileExists() {
        File staffFile = new File(DATA_DIRECTORY_PATH, STAFF_FILE_PATH);
        if (!staffFile.exists()) {
            try {
                if (staffFile.createNewFile()) {
                    System.out.println("Staff file created: " + staffFile.getPath());
                } else {
                    System.out.println("Failed to create staff file: " + staffFile.getPath());
                }
            } catch (IOException e) {
                System.out.println("Error while creating the file: " + e.getMessage());
            }
        }
    }

    /**
     * Add staff member.
     *
     * @param member the member
     */
    public void addStaffMember(StaffMember member) {
        staffMembersHashMap.put(member.getId(), member);
        writeToFile();
    }

    /**
     * Gets staff member.
     *
     * @param id the id
     * @return the staff member
     */
    public StaffMember getStaffMember(int id) {
        return staffMembersHashMap.get(id);
    }

    /**
     * Remove staff member.
     *
     * @param id the id
     */
    public void removeStaffMember(int id) {
        staffMembersHashMap.remove(id);
        writeToFile();
    }

    /**
     * Remove all staff members.
     */
    public void removeAllStaffMembers() {
        staffMembersHashMap.clear();
        writeToFile();
    }

    /**
     * Update total workload of staff member
     * @param staffId the staff member id
     * @param newTotalWorkload the new total workload
     */
    public void updateTotalWorkload(int staffId, int newTotalWorkload) {
        StaffMember staffMember = staffMembersHashMap.get(staffId);
        staffMember.setTotalWorkload(newTotalWorkload);
        staffMembersHashMap.put(staffMember.getId(), staffMember);
        writeToFile();
    }
}
