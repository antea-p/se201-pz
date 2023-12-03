package org.gameplanner;

import java.util.Objects;

public class StatusDisplay {

    public static String getLabel(Status status) {
        Objects.requireNonNull(status, "Status value is null!");

        if (status == Status.COMPLETED_100) {
            return "100% Completed";
        }
        else if (status == Status.IN_PROGRESS) {
            return "In Progress";
        } else {
            String status_ = status.toString();
            return status_.substring(0, 1).toUpperCase() + status_.substring(1).toLowerCase();
        }
    }

}
