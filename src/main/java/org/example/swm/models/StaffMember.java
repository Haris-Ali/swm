package org.example.swm.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Represents a staff member in the system.
 * Stores staff-related details and provides access to them.
 * Implements Serializable for persistence.
 */
public class StaffMember implements Serializable {
    //<-***** https://www.baeldung.com/java-serial-version-uid - START
    @Serial
    private static final long serialVersionUID = 1L;
    //<-***** https://www.baeldung.com/java-serial-version-uid - END
    private final int id; // Unique id for staff member
    private String name; // Name of staff member
    private Double contractType; // Contract type (full-time/part-time) of staff member
    private String subjectArea; // Subject area like computing or business etc
    private String lineManager; // Line manager of staff member
    private int totalWorkload; // Total workload of staff member calculated based on duties
    private int atsrWorkload;
    private int tsWorkload;
    private int tlrWorkload;
    private int saWorkload;
    private int otherWorkload;

    /**
     * Constructor for a new staff member with given values of name, contract type, subject area & line manager
     *
     * @param name         Name of staff member
     * @param contractType Contract type (full-time/part-time) of staff member
     * @param subjectArea  Subject area like computing or business etc
     * @param lineManager  Line manager of staff member
     */
    public StaffMember(String name, Double contractType, String subjectArea, String lineManager) {
        this.id = IdGenerator.getInstance().getNextStaffMemberId();
        this.name = name;
        this.contractType = contractType;
        this.subjectArea = subjectArea;
        this.lineManager = lineManager;
        this.totalWorkload = 0;
        this.atsrWorkload = 0;
        this.tsWorkload = 0;
        this.tlrWorkload = 0;
        this.saWorkload = 0;
        this.otherWorkload = 0;
    }

    // Getters for each property of staff member
    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() { return id; }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets contract type.
     *
     * @return the contract type
     */
    public Double getContractType() { return contractType; }

    /**
     * Gets subject area.
     *
     * @return the subject area
     */
    public String getSubjectArea() { return subjectArea; }

    /**
     * Gets line manager.
     *
     * @return the line manager
     */
    public String getLineManager() { return lineManager; }

    /**
     * Gets total workload.
     *
     * @return the total workload
     */
    public int getTotalWorkload() { return totalWorkload; }

    /**
     * Gets atsr workload.
     *
     * @return the atsr workload
     */
    public int getAtsrWorkload() { return atsrWorkload; }

    /**
     * Gets ts workload.
     *
     * @return the ts workload
     */
    public int getTsWorkload() { return tsWorkload; }

    /**
     * Gets tlr workload
     * @return the tlr workload
     */
    public int getTlrWorkload() { return tlrWorkload; }

    /**
     * Gets sa workload.
     *
     * @return the sa workload
     */
    public int getSaWorkload() { return saWorkload; }

    /**
     * Gets other workload.
     *
     * @return the other workload
     */
    public int getOtherWorkload() { return otherWorkload; }


    // Setters for each property of staff member
    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets contract type.
     *
     * @param contractType the contract type
     */
    public void setContractType(Double contractType) { this.contractType = contractType; }

    /**
     * Sets subject area.
     *
     * @param subjectArea the subject area
     */
    public void setSubjectArea(String subjectArea) { this.subjectArea = subjectArea; }

    /**
     * Sets line manager.
     *
     * @param lineManager the line manager
     */
    public void setLineManager(String lineManager) { this.lineManager = lineManager; }

    /**
     * Sets total workload.
     *
     * @param totalWorkload the total workload
     */
    public void setTotalWorkload(int totalWorkload) { this.totalWorkload = totalWorkload; }

    /**
     * Sets atsr workload.
     *
     * @param atsrWorkload the atsr workload
     */
    public void setAtsrWorkload(int atsrWorkload) { this.atsrWorkload = atsrWorkload; }

    /**
     * Sets ts workload.
     *
     * @param tsWorkload the ts workload
     */
    public void setTsWorkload(int tsWorkload) { this.tsWorkload = tsWorkload; }

    /**
     * Sets tlr workload
     * @param tlrWorkload the tlr workload
     */
    public void setTlrWorkload(int tlrWorkload) { this.tlrWorkload = tlrWorkload; }

    /**
     * Sets sa workload.
     *
     * @param saWorkload the sa workload
     */
    public void setSaWorkload(int saWorkload) { this.saWorkload = saWorkload; }

    /**
     * Sets other workload.
     *
     * @param otherWorkload the other workload
     */
    public void setOtherWorkload(int otherWorkload) { this.otherWorkload = otherWorkload; }

    /**
     * Override toString method
     * @return the formatted string of staff member's properties
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Contract Type: " + contractType + ", Subject Area: " + subjectArea
                + ", Line Manager: " + lineManager + ", Total Workload: " + totalWorkload + ", Atsr Workload: " + atsrWorkload
                + ", Ts Workload: " + tsWorkload + ", Sa Workload: " + saWorkload + ", Other Workload: " + otherWorkload;
    }
}
