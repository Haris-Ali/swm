package org.example.swm.models;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an id generator helper class in the system.
 * Stores seperate classes for staff member and duty id.
 * Implements Serializable for persistence.
 */
//<-***** https://stackoverflow.com/questions/15029445/java-static-variable-for-auto-increment-userid-objectoutputstream - START
public class IdGenerator implements Serializable {
    /**
     * The class for staff member id.
     */
    public static class StaffMemberId {
        private static int nextId = 1;

        /**
         * Next id int.
         *
         * @return the int
         */
        public static int nextId() {
            return nextId++;
        }
    }

    /**
     * The class for duty id.
     */
    public static class DutyId {
        private static int nextId = 1;

        /**
         * Next id int.
         *
         * @return the int
         */
        public static int nextId() {
            return nextId++;
        }
    }
}
//<-***** https://stackoverflow.com/questions/15029445/java-static-variable-for-auto-increment-userid-objectoutputstream - END
