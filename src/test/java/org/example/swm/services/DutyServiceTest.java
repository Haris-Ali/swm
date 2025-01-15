package org.example.swm.services;

import org.example.swm.models.Duty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DutyServiceTest {
    MockDutyService mockDutyService;

    /**
     * Setup method to instantiate duty service mock instance
     */
    @BeforeEach
    void setUp() {
        mockDutyService = new MockDutyService();
    }

    /**
     * Test to verify file read/duty retrieval
     */
    @Test
    void readFromFileTest() {
        Duty dutyMock = new Duty(1, "ATSR", "Formal Scheduled Teaching", "TEST", "Trimester 1", 4, 12);
        mockDutyService.addDutyAgainstStaffMember(1, dutyMock);
        HashMap<Integer, Duty> map = mockDutyService.readFromFile("", 1);
        assertNotNull(map);
        boolean expected = mockDutyService.isReadToFileCalled();
        assertTrue(expected);
    }

    /**
     * Test to verify file write/duty addition
     */
    @Test
    void writeToFileTest() {
        Duty dutyMock = new Duty(1, "ATSR", "Formal Scheduled Teaching", "TEST", "Trimester 1", 4, 12);
        mockDutyService.addDutyAgainstStaffMember(1, dutyMock);
        boolean expected = mockDutyService.isWriteToFileCalled();
        assertTrue(expected);
    }

    /**
     * Test to verify duty removal by id
     */
    @Test
    void removeDutyTest() {
        Duty dutyMock = new Duty(1, "ATSR", "Formal Scheduled Teaching", "TEST", "Trimester 1", 4, 12);
        mockDutyService.addDutyAgainstStaffMember(1, dutyMock);
        mockDutyService.removeDuty(1, dutyMock);
        boolean expected = mockDutyService.isWriteToFileCalled();
        assertTrue(expected);
        Duty retrievedDutyMock = mockDutyService.getDutyAgainstStaffMember(1, 1);
        assertNull(retrievedDutyMock);
    }

}