package org.example.swm.models;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an hour category in the system.
 * Stores hour category related details and provides access to them.
 * Implements Serializable for persistence.
 */
public class HourCategory implements Serializable {
    //<-***** https://www.baeldung.com/java-serial-version-uid - START
    @Serial
    private static final long serialVersionUID = 1L;
    //<-***** https://www.baeldung.com/java-serial-version-uid - END
    private final String name;
    private int hours;

    /**
     * Instantiates a new Hour category.
     *
     * @param name the name
     */
    public HourCategory(String name) {
        this.name = name;
        this.hours = 0;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets hours.
     *
     * @return the hours
     */
    public int getHours() { return hours; }

    /**
     * Sets hours.
     *
     * @param hours the hours
     */
    public void setHours(int hours) { this.hours = hours; }

    @Override
    public String toString() {
        return name + ": " + hours + " hours";
    }
}
