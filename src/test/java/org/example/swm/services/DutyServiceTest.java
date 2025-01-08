package org.example.swm.services;

import org.example.swm.models.Duty;
import org.example.swm.models.StaffMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DutyServiceTest {
    DutyService dutyServiceMock;
    StaffService staffServiceMock;

    /**
     * Setup method to instantiate duty service mock instance
     */
    @BeforeEach
    void setUp() {
        dutyServiceMock = new DutyService();
        staffServiceMock = new StaffService();
    }

//    /**
//     * Test to verify file read/ duty against staff member retrieval
//     */
//    @Test
//    void readFromFileTest() {
//        StaffMember staffMemberMock = staffServiceMock.getStaffMember(1);
//        Duty dutyMock = dutyServiceMock.getDutyAgainstStaffMember(staffMemberMock, 1);
//        assertNotNull(dutyMock);
//        assertEquals("ATSR", dutyMock.getDutyType());
//    }
//
//    /**
//     * Test to verify file write/ duty against staff member addition
//     */
//    @Test
//    void writeToFileTest() {
//        StaffMember staffMemberMock = staffServiceMock.getStaffMember(1);
//        Duty dutyMock = new Duty("ATSR", "Formal Scheduled Teaching", "Test", "Trimester 1", 3, 12);
//        dutyServiceMock.addDutyAgainstStaffMember(staffMemberMock, dutyMock);
//        DutyService newDutyServiceMock = new DutyService();
//        Duty retrievedDutyMock = newDutyServiceMock.getDutyAgainstStaffMember(staffMemberMock, 2);
//        assertNotNull(retrievedDutyMock);
//        assertEquals("Formal Scheduled Training", retrievedDutyMock.getActivityType());
//    }
//
//    /**
//     * Test to verify duty removal by id
//     */
//    @Test
//    void removeDutyTest() {
//        StaffMember staffMemberMock = staffServiceMock.getStaffMember(1);
//        Duty dutyMock = dutyServiceMock.getDutyAgainstStaffMember(staffMemberMock, 2);
//        dutyServiceMock.removeDuty(staffMemberMock, dutyMock);
//
//        Duty retrievedDutyMock = dutyServiceMock.getDutyAgainstStaffMember(staffMemberMock, 2);
//        assertNull(retrievedDutyMock);
//    }

}