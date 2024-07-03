package labyrinth.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import labyrinth.game.LabyrinthApplication;
import labyrinth.results.StatsManager;
import lombok.Getter;

import java.io.IOException;

public class MenuController {

    @FXML
    private TextField playerInput;

    @FXML
    private Button startButton;

    @FXML
    private Button scoreboardButton;

    @Getter
    private static String username;

    private final StatsManager statsManager = StatsManager.getInstance();


    public void initialize(){
        startButton.setDisable(true);

        playerInput.textProperty().addListener((observable, oldValue, newValue) -> {startButton.setDisable(newValue.trim().isEmpty());
        });
    }

    @FXML
    public void startAction() throws Exception {
        username = playerInput.getText();
        LabyrinthApplication.changeScene("/game.fxml", 800, 900);
    }

    @FXML
    private void openScoreboard() throws IOException{
        Stage stage = (Stage) scoreboardButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scoreboard.fxml"));
        stage.setTitle("Scoreboard");
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void handleExitButtonClicked() {
        LabyrinthApplication.closeApplication();
    }
}


