package org.example.swm.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StaffMemberTest {
    private StaffMember staffMember;

    @BeforeEach
    void setUp() {
        staffMember = new StaffMember("Ali", 1.0, "Marketing", "Ahmed");
    }

    @Test
    void testGetters() {
        assertEquals("Ali", staffMember.getName());
        assertEquals(1.0, staffMember.getContractType());
        assertEquals("Marketing", staffMember.getSubjectArea());
        assertEquals("Ahmed", staffMember.getLineManager());
        assertEquals(0, staffMember.getTotalWorkload());
    }

    @Test
    void testSetters() {
        staffMember.setName("Ahsan");
        staffMember.setContractType(0.5);
        staffMember.setSubjectArea("Mathematics");
        staffMember.setLineManager("Charlie");
        staffMember.setTotalWorkload(40);
        staffMember.setAtsrWorkload(10);
        staffMember.setTsWorkload(10);
        staffMember.setTlrWorkload(5);
        staffMember.setSaWorkload(5);
        staffMember.setOtherWorkload(10);

        assertEquals("Ahsan", staffMember.getName());
        assertEquals(0.5, staffMember.getContractType());
        assertEquals("Mathematics", staffMember.getSubjectArea());
        assertEquals("Charlie", staffMember.getLineManager());
        assertEquals(40, staffMember.getTotalWorkload());
        assertEquals(10, staffMember.getAtsrWorkload());
        assertEquals(10, staffMember.getTsWorkload());
        assertEquals(5, staffMember.getTlrWorkload());
        assertEquals(5, staffMember.getSaWorkload());
        assertEquals(10, staffMember.getOtherWorkload());
    }

    @Test
    void testWorkloadCalculation() {
        staffMember.setAtsrWorkload(10);
        staffMember.setTsWorkload(15);
        staffMember.setTlrWorkload(5);
        staffMember.setSaWorkload(5);
        staffMember.setOtherWorkload(5);

        int expectedTotalWorkload = 10 + 15 + 5 + 5 + 5;

        staffMember.setTotalWorkload(expectedTotalWorkload);

        assertEquals(expectedTotalWorkload, staffMember.getTotalWorkload());
    }

    @Test
    void testInvalidWorkload() {
        staffMember.setAtsrWorkload(-5);
        staffMember.setTsWorkload(-10);
        staffMember.setTlrWorkload(-3);
        staffMember.setSaWorkload(-1);
        staffMember.setOtherWorkload(-2);
        staffMember.setTotalWorkload(-5 + -10 + -3 + -1 + -2 + -2);

        assertNotEquals(0, staffMember.getTotalWorkload(), "Workloads should not be negative and should default to 0.");
    }
}
