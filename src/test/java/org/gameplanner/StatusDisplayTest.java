package org.gameplanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusDisplayTest {

    @Test
    void test100Completed() {
        assertEquals("100% Completed", StatusDisplay.getLabel(Status.COMPLETED_100));
    }

    @Test
    void testInProgress() {
        assertEquals("In Progress", StatusDisplay.getLabel(Status.IN_PROGRESS));
    }

    @Test
    void testOtherStatuses() {
        assertEquals("Backlog", StatusDisplay.getLabel(Status.BACKLOG));
        assertEquals("Abandoned", StatusDisplay.getLabel(Status.ABANDONED));
        assertEquals("Completed", StatusDisplay.getLabel(Status.COMPLETED));
    }

    @Test
    void testNullStatus() {
        assertThrows(NullPointerException.class, () -> StatusDisplay.getLabel(null));

    }
}