package org.example.swm.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DutyTest {
    private Duty duty;

    @BeforeEach
    void setUp() {
        duty = new Duty(1, "ATSR", "Formal Scheduled Training", "Test", "Trimester 1", 4, 12);
        duty.calculateHourCategories();
    }

    @Test
    void testGetters() {
        assertEquals("ATSR", duty.getDutyType());
        assertEquals("Formal Scheduled Training", duty.getActivityType());
        assertEquals("Test", duty.getDescription());
        assertEquals("Trimester 1", duty.getWeeks());
        assertEquals(4, duty.getDuration());
        assertEquals(12, duty.getInstances());
        assertEquals(48, duty.getHours());
    }

    @Test
    void testSetters() {
        duty.setDutyType("ATSR1");
        duty.setActivityType("Formal");
        duty.setDescription("Test1");
        duty.setWeeks("Trimester 2");
        duty.setDuration(3);
        duty.setInstances(12);
        duty.setHours();

        assertEquals("ATSR1", duty.getDutyType());
        assertEquals("Formal", duty.getActivityType());
        assertEquals("Test1", duty.getDescription());
        assertEquals("Trimester 2", duty.getWeeks());
        assertEquals(3, duty.getDuration());
        assertEquals(12, duty.getInstances());
        assertEquals(36, duty.getHours());
    }

    @Test
    void testCorrectWorkload() {
        assertEquals(106, duty.getWorkloadForDuty());
    }

    @Test
    void testWrongWorkload() {
        assertNotEquals(48, duty.getWorkloadForDuty());
    }
}
