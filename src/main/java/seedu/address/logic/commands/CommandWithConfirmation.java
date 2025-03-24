package seedu.address.logic.commands;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Represent commands that need confirmation before they are executed.
 */
public interface CommandWithConfirmation {
    boolean executeWithConfirmation();

    /**
     * A Confirmation Dialog for commands that are not easily reversible.
     * @param headerText The header text
     * @param contentText The content text
     * @return is command confirmed
     */
    default boolean showConfirmationDialog(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}
