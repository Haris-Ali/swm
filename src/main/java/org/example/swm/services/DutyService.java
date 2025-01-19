package org.example.swm.services;

import org.example.swm.models.Duty;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

/**
 * The class Duty service.
 */
public class DutyService {
    private static final String DATA_DIRECTORY_PATH = "data";
    private static final String DUTY_DIRECTORY_PATH = "data/duties"; // Path where duties will be stored
    private HashMap<Integer, Duty> dutiesHashMap; // Duty hash map to store duties

    /**
     * Read from file hash map.
     *
     * @param dutyFilePath the duty file path
     * @return the hash map
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, Duty> readFromFile(String dutyFilePath, int staffId) {
        HashMap<Integer, Duty> map = new HashMap<>();
        dataDirectoryExists();
        dutyDirectoryExists();
        dutyFileExists(staffId);

        File dutyFile = new File(dutyFilePath);
        if (!dutyFile.exists()) {
            System.out.println("File does not exist: " + dutyFile.getPath());
            return map;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dutyFilePath))) {
            map = (HashMap<Integer, Duty>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The following error occurred: " + e.getMessage());
        }
        return map;
    }

    /**
     * Write to file.
     */
    public void writeToFile(String dutyFilePath, int staffId) {
        dataDirectoryExists();
        dutyDirectoryExists();
        dutyFileExists(staffId);

        File dutyFile = new File(dutyFilePath);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dutyFile))) {
            out.writeObject(dutiesHashMap);
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

    public void dutyDirectoryExists() {
        File dutyDirectory = new File(DUTY_DIRECTORY_PATH);
        if (!dutyDirectory.exists()) {
            if (dutyDirectory.mkdir()) {
                System.out.println("Data directory created: " + dutyDirectory.getPath());
            } else {
                System.out.println("Failed to create data directory: " + dutyDirectory.getPath());
            }
        }
    }

    /**
     * Check and create an empty staff file
     */
    public void dutyFileExists(int staffId) {
        String filePath = DUTY_DIRECTORY_PATH + "/" + "duties_staff_" + staffId + ".dat";
        File dutyFile = new File(filePath);
        if (!dutyFile.exists()) {
            try {
                if (dutyFile.createNewFile()) {
                    System.out.println("Staff file created: " + dutyFile.getPath());
                } else {
                    System.out.println("Failed to create staff file: " + dutyFile.getPath());
                }
            } catch (IOException e) {
                System.out.println("Error while creating the file: " + e.getMessage());
            }
        }
    }

    /**
     * A function that takes staffId and checks if associated duty file exists or not
     * @param staffId the id of the staff member
     * @return boolean key to represent if duty file exists
     */
    public boolean isDutyFileCreated(int staffId) {
        String filePath = DUTY_DIRECTORY_PATH + "/" + "duties_staff_" + staffId + ".dat";
        File dutyFile = new File(filePath);
        return dutyFile.exists();
    }

    /**
     * Add duty against staff member.
     * Generates dynamic file path based on staff member id
     *
     * @param staffId the staff member id
     * @param duty   the duty
     */
    public void addDutyAgainstStaffMember(int staffId, Duty duty) {
        String dutyFilePath = "data/duties/duties_staff_" + staffId + ".dat";
        dutiesHashMap = readFromFile(dutyFilePath, staffId);
        dutiesHashMap.put(duty.getId(), duty);
        writeToFile(dutyFilePath, staffId);
    }

    /**
     * Gets a single duty against staff member.
     *
     * @param staffId the staff member id
     * @param dutyId the duty id
     * @return the duty against staff member
     */
    public Duty getDutyAgainstStaffMember(int staffId, int dutyId) {
        String dutyFilePath = "data/duties/duties_staff_" + staffId + ".dat";
        dutiesHashMap = readFromFile(dutyFilePath, staffId);
        return dutiesHashMap.get(dutyId);
    }

    /**
     * Gets all duties against staff member.
     *
     * @param staffId the staff member id
     * @return the duties against staff member
     */
    public HashMap<Integer, Duty> getDutiesAgainstStaffMember(int staffId) {
        String dutyFilePath = "data/duties/duties_staff_" + staffId + ".dat";
        dutiesHashMap = readFromFile(dutyFilePath, staffId);
        return dutiesHashMap;
    }

    /**
     * Gets all duties against all staff members.
     *
     * @return all duties
     */
    public HashMap<Integer, Duty> getAllDuties() {
        HashMap<Integer, Duty> allDuties = new HashMap<>();

        File dutiesFolder = new File(DUTY_DIRECTORY_PATH);
        for (File file : Objects.requireNonNull(dutiesFolder.listFiles())) {
            String dutyFilePath = file.getPath();
            StringBuilder number = new StringBuilder();
            for (char c : dutyFilePath.toCharArray()) {
                if (Character.isDigit(c)) { number.append(c); }
            }
            int extractedStaffId = Integer.parseInt(number.toString());
            HashMap<Integer, Duty> dutiesFromFile = readFromFile(dutyFilePath, extractedStaffId);
            allDuties.putAll(dutiesFromFile);
        }
        return allDuties;
    }

    /**
     * Remove a single duty against a specific staff member.
     *
     * @param staffId the staff member id
     * @param duty   the duty
     */
    public void removeDuty(int staffId, Duty duty) {
        if (isDutyFileCreated(staffId)) {
            String dutyFilePath = "data/duties/duties_staff_" + staffId + ".dat";
            dutiesHashMap = readFromFile(dutyFilePath, staffId);
            dutiesHashMap.remove(duty.getId());
            writeToFile(dutyFilePath, staffId);
        }
    }

    /**
     * Remove all duties against staff member.
     *
     * @param staffId the staff member id
     */
    public void removeAllDutiesAgainstStaffMember(int staffId) {
        if (isDutyFileCreated(staffId)) {
            String dutyFilePath = "data/duties/duties_staff_" + staffId + ".dat";
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(dutyFilePath)).getFile());
            file.delete();
        }
    }

    /**
     * Remove all duties.
     */
    public void removeAllDuties() {
        try {
            File dutiesFolder = new File(DATA_DIRECTORY_PATH, DUTY_DIRECTORY_PATH);

            for (File file : Objects.requireNonNull(dutiesFolder.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".dat")) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        System.out.println("Failed to delete file: " + file.getName());
                    }
                }
            }
            System.out.println("All duties have been removed.");
        } catch (Exception e) {
            System.out.println("Error while removing all duties: " + e.getMessage());
        }
    }

    /**
     * Returns total duties count
     * @return the count of total duties
     */
    public int getTotalDutiesCount() {
        HashMap<Integer, Duty> duties = getAllDuties();
        return duties.size();
    }
}
