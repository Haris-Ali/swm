package org.example.swm.services;

import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StaffServiceTest {
    MockStaffService mockStaffService;

    /**
     * Setup method to instantiate staff service mock instance
     */
    @BeforeEach
    void setUp() {
        mockStaffService = new MockStaffService();
    }

    /**
     * Test to verify file read/staff member retrieval by id
     */
    @Test
    void readFromFileTest() {
        StaffMember staffMemberMock = new StaffMember("John", 1.0, "Computing", "Sam");
        mockStaffService.addStaffMember(staffMemberMock);
        HashMap<Integer, StaffMember> map = mockStaffService.readFromFile();
        assertNotNull(map);
        boolean expected = mockStaffService.isReadToFileCalled();
        assertTrue(expected);
//        StaffMember retrievedStaffMemberMock = mockStaffService.getStaffMember(1);
//        assertNotNull(retrievedStaffMemberMock);
//        assertEquals("John", retrievedStaffMemberMock.getName());
    }

    /**
     * Test to verify file write/staff member addition
     */
    @Test
    void writeToFileTest() {
        StaffMember staffMemberMock = new StaffMember("John", 1.0, "Computing", "Sam");
        mockStaffService.addStaffMember(staffMemberMock);
        boolean expected = mockStaffService.isWriteToFileCalled();
        assertTrue(expected);
//        StaffMember retrievedStaffMemberMock = mockStaffService.getStaffMember(staffMemberMock.getId());
//        assertNotNull(retrievedStaffMemberMock);
//        assertEquals("John", retrievedStaffMemberMock.getName());
    }

    /**
     * Test to verify staff member removal by id
     */
    @Test
    void removeStaffMemberTest() {
        StaffMember staffMemberMock = new StaffMember("John", 1.0, "Computing", "Sam");
        mockStaffService.addStaffMember(staffMemberMock);
        mockStaffService.removeStaffMember(1);
        boolean expected = mockStaffService.isWriteToFileCalled();
        assertTrue(expected);
        StaffMember retrievedStaffMemberMock = mockStaffService.getStaffMember(1);
        assertNull(retrievedStaffMemberMock);
    }
}