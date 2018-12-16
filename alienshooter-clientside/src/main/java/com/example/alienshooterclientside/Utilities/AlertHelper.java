package com.example.alienshooterclientside.Utilities;
import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * This AlertHelper class is used to warn the player like when the player forgets to
 * fill a textbox or when the player enters a wrong password.
 *
 * It contains only one function which shows the warning.
 */
public class AlertHelper {

    /**
     * This function creates a window to warn the player showing alertType, title and message.
     * @param alertType
     * @param owner
     * @param title
     * @param message
     */
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
