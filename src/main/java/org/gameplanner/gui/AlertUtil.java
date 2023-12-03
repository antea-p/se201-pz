package org.gameplanner.gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertUtil {

    public static void displayAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);

        // Workaround za premalen alert: https://stackoverflow.com/a/55200733
        alert.setResizable(true);
        alert.onShownProperty()
            .addListener(e -> Platform.runLater(() -> alert.setResizable(false)));

        // Wraparound opcija: https://stackoverflow.com/a/46395543
        StringBuilder sb = new StringBuilder(message);
        for (int i = 0; i < message.length(); i += 200) {
            sb.insert(i, "\n");
        }

        Label messageLbl = new Label(sb.toString());
        alert.getDialogPane().setContent(messageLbl);

        // On-top opcija: https://stackoverflow.com/a/43007782
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        stage.toFront();
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> alert.close());
    }

}
