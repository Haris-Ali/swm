package org.example.swm.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a duty in the system.
 * Stores duty-related details and provides access to them.
 * Implements Serializable for persistence.
 */
public class Duty implements Serializable {
    //<-***** https://www.baeldung.com/java-serial-version-uid - START
    @Serial
    private static final long serialVersionUID = 1L;
    //<-***** https://www.baeldung.com/java-serial-version-uid - END
    private final int id;
    private final int staffId;
    private String dutyType;
    private String activityType;
    private String description;
    private String weeks;
    private int duration;
    private int instances;
    private int hours;
    private final ArrayList<HourCategory> hourCategories;

    /**
     * Instantiates a new Duty.
     *
     * @param dutyType     the duty type
     * @param activityType the activity type
     * @param description  the description
     * @param weeks        the weeks
     * @param duration     the duration
     * @param instances    the instances
     */

    public Duty(int staffId, String dutyType, String activityType, String description, String weeks, int duration, int instances) {
        this.id = IdGenerator.DutyId.nextId();
        this.staffId = staffId;
        this.dutyType = dutyType;
        this.activityType = activityType;
        this.description = description;
        this.weeks = weeks;
        this.duration = duration;
        this.instances = instances;
        this.hours = duration * instances;
        this.hourCategories = initialiseHourCategories();
    }

    // Getters for each property of staff member
    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() { return id; }

    /**
     * Gets staff id.
     * @return the associated staff id
     */
    public int getStaffId() { return staffId; }

    /**
     * Gets duty type.
     *
     * @return the duty type
     */
    public String getDutyType() { return dutyType; }

    /**
     * Gets activity type.
     *
     * @return the activity type
     */
    public String getActivityType() { return activityType; }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Gets weeks.
     *
     * @return the weeks
     */
    public String getWeeks() { return weeks; }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() { return duration; }

    /**
     * Gets instances.
     *
     * @return the instances
     */
    public int getInstances() { return instances; }

    /**
     * Gets hours.
     *
     * @return the hours
     */
    public int getHours() { return hours; }

    /**
     * Gets hour categories.
     *
     * @return the hour categories
     */
    public ArrayList<HourCategory> getHourCategories() { return hourCategories; }


    // Setters for each property of staff member
    /**
     * Sets duty type.
     *
     * @param dutyType the duty type
     */
    public void setDutyType(String dutyType) { this.dutyType = dutyType; }

    /**
     * Sets activity type.
     *
     * @param activityType the activity type
     */
    public void setActivityType(String activityType) { this.activityType = activityType; }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Sets weeks.
     *
     * @param weeks the weeks
     */
    public void setWeeks(String weeks) { this.weeks = weeks; }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) { this.duration = duration; }

    /**
     * Sets instances.
     *
     * @param instances the instances
     */
    public void setInstances(int instances) { this.instances = instances; }

    /**
     * Sets hours.
     */
    public void setHours() { this.hours = duration * instances; }

    /**
     * Sets hour categories for each duty
     * @return list of hour categories
     */
    private ArrayList<HourCategory> initialiseHourCategories() {
        ArrayList<HourCategory> categories = new ArrayList<>();
        categories.add(new HourCategory("ATSR"));
        categories.add(new HourCategory("TS"));
        categories.add(new HourCategory("TLR"));
        categories.add(new HourCategory("SA"));
        categories.add(new HourCategory("Other"));
        return categories;
    }

    public int getAtsrHours() {
        return hourCategories.get(0).getHours();
    }

    public int getTsHours() {
        return hourCategories.get(1).getHours();
    }

    public int getTlrHours() {
        return hourCategories.get(2).getHours();
    }

    public int getSaHours() {
        return hourCategories.get(3).getHours();
    }

    public int getOtherHours() {
        return hourCategories.get(4).getHours();
    }

    /**
     * Calculate hour categories.
     */
    public void calculateHourCategories() {
        switch (getDutyType()) {
            case "ATSR" -> {
                hourCategories.get(0).setHours(hours);
                hourCategories.get(1).setHours((int) Math.ceil(hours * 1.2));
            }
            case "TLR" -> hourCategories.get(2).setHours(hours);
            case "SA" -> hourCategories.get(3).setHours(hours);
            case "Other" -> hourCategories.get(4).setHours(hours);
        }
    }

    /**
     * Gets workload for duty.
     *
     * @return the workload for duty
     */
    public int getWorkloadForDuty() {
        int total = 0;
        for (HourCategory hourCategory : hourCategories) {
            total += hourCategory.getHours();
        }
        System.out.println("Total workload for this duty is: " + total);
        return total;
    }

    /**
     * Override toString method
     * @return the formatted string of duty's properties
     */
    @Override
    public String toString() {
        return "Duty ID: " + id + ", Staff ID: " + staffId + ", Duty Type: " + dutyType + ", Activity Type: " + activityType +
                ", Description: " + description + ", Weeks: " + weeks + ", Duration: " + duration +
                ", Instances: " + instances + ", Hours: " + hours + ", Hour Categories: " + hourCategories;
    }
}
