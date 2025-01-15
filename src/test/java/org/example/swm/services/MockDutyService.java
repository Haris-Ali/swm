package org.example.swm.services;

import org.example.swm.models.Duty;

import java.util.HashMap;

public class MockDutyService extends DutyService {
    private HashMap<Integer, Duty> mockDutiesHashMap;; // Hash map for staff members
    private boolean writeToFileCalled = false;
    private boolean readToFileCalled = false;

    public MockDutyService() {
        mockDutiesHashMap = new HashMap<>();
    }

    /**
     * Simulate read from file behavior without actual file I/O
     */
    @Override
    public HashMap<Integer, Duty> readFromFile(String dutyFilePath, int staffId) {
        readToFileCalled = true;
        return mockDutiesHashMap;
    }

    /**
     * Simulate write to file behavior without actual file I/O
     */
    @Override
    public void writeToFile(String dutyFilePath, int staffId) {
        writeToFileCalled = true;
    }

    /**
     * Test for adding a duty by simulating file read/write
     * @param staffId the staff member id
     * @param duty   the duty
     */
    @Override
    public void addDutyAgainstStaffMember(int staffId, Duty duty) {
        mockDutiesHashMap.put(duty.getId(), duty);
        writeToFile("", staffId);
    }

    /**
     * Test for removing a duty by simulating file read/write
     * @param staffId the staff member id
     * @param duty   the duty
     */
    @Override
    public void removeDuty(int staffId, Duty duty) {
        mockDutiesHashMap.remove(duty.getId());
        writeToFile("", staffId);
    }

    /**
     * Helper function to get write to file boolean flag
     */
    public boolean isWriteToFileCalled() {
        return writeToFileCalled;
    }

    /**
     * Helper function to get read from file boolean flag
     */
    public boolean isReadToFileCalled() {
        return readToFileCalled;
    }
}
