package org.example.swm.services;

import org.example.swm.models.StaffMember;

import java.util.HashMap;

public class MockStaffService extends StaffService {
    private final HashMap<Integer, StaffMember> mockStaffMembersHashMap; // Hash map for staff members
    private boolean writeToFileCalled = false;
    private boolean readToFileCalled = false;

    public MockStaffService() {
        mockStaffMembersHashMap = new HashMap<>();
    }

    /**
     * Simulate read from file behavior without actual file I/O
     */
    @Override
    public HashMap<Integer, StaffMember> readFromFile() {
        readToFileCalled = true;
        return mockStaffMembersHashMap;
    }

    /**
     * Simulate write to file behavior without actual file I/O
     */
    @Override
    public void writeToFile() {
        writeToFileCalled = true;
    }

    /**
     * Simulate add staff member
     */
    @Override
    public void addStaffMember(StaffMember staffMember) {
        mockStaffMembersHashMap.put(staffMember.getId(), staffMember);
        writeToFile();
    }

    /**
     * Simulate get staff member
     */
    @Override
    public StaffMember getStaffMember(int id) {
        return mockStaffMembersHashMap.get(id);
    }

    /**
     * Simulate remove staff member
     */
    @Override
    public void removeStaffMember(int id) {
        mockStaffMembersHashMap.remove(id);
        writeToFile();
    }

    /**
     * Simulate remove all staff member
     */
    @Override
    public void removeAllStaffMembers() {
        mockStaffMembersHashMap.clear();
        writeToFile();
    }

    /**
     * Simulate update workload of staff member
     */
    @Override
    public void updateTotalWorkload(int staffId, int newTotalWorkload) {
        StaffMember staffMember = mockStaffMembersHashMap.get(staffId);
        staffMember.setTotalWorkload(newTotalWorkload);
        mockStaffMembersHashMap.put(staffId, staffMember);
        writeToFile();
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
