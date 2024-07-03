package labyrinth.game;

import labyrinth.results.StatsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.tinylog.Logger;

public class PopupController {

    public Label resultLabel;
    public Button menuButton;
    @FXML
    private Button closeButton;
    private final StatsManager statsManager = StatsManager.getInstance();

    public void setMessage(String message) {
        resultLabel.setText(message);
    }


    public static void showPopup(Stage ownerStage, String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PopupController.class.getResource("/popup.fxml"));
            Parent popupRoot = fxmlLoader.load();

            PopupController controller = fxmlLoader.getController();

            controller.setMessage(message);

            Stage popupStage = new Stage();
            popupStage.setTitle("Game Over");
            popupStage.initModality(Modality.WINDOW_MODAL); // Block events to other windows
            popupStage.initOwner(ownerStage);
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait();
        } catch (Exception e) {
            Logger.warn("Failed to load PopUp Window: " + e.getMessage());
        }
    }
    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        statsManager.writePlayers();
        LabyrinthApplication.closeApplication();
    }

    public void handleMenu() throws Exception {
        Stage stage = (Stage) menuButton.getScene().getWindow();
        stage.close();
        statsManager.writePlayers();
        LabyrinthApplication.changeScene("/menu.fxml", 900, 700);
    }
}