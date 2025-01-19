package org.example.swm.models;

import java.io.Serializable;

import java.io.*;
import java.util.Objects;

/**
 * Represents an id generator helper class in the system.
 * Stores seperate classes for staff member and duty id.
 * Implements Serializable for persistence.
 */
//<-***** https://stackoverflow.com/questions/15029445/java-static-variable-for-auto-increment-userid-objectoutputstream - START
public class IdGenerator implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "id_generator_state.dat";

    private static IdGenerator instance;

    private int staffMemberNextId = 1;
    private int dutyNextId = 1;

    private IdGenerator() {}

    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = loadState();
        }
        return instance;
    }

    /**
     * The method for incrementing staff member id.
     */
    public int getNextStaffMemberId() {
        return staffMemberNextId++;
    }

    /**
     * The method for incrementing duty id.
     */
    public int getNextDutyId() {
        return dutyNextId++;
    }

    public static void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static IdGenerator loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (IdGenerator) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new IdGenerator(); // Return a new instance if file is not found or an error occurs
        }
    }
}
//<-***** https://stackoverflow.com/questions/15029445/java-static-variable-for-auto-increment-userid-objectoutputstream - END
