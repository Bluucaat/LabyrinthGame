package labyrinth.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class  LabyrinthApplication extends Application {

    public static Stage primaryStage;


    @Override
    public void start(Stage stage) throws IOException {
        LabyrinthApplication.primaryStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/menu.fxml")));
        stage.setTitle("Labyrinth Puzzle");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void changeScene(String fxml, int width, int height) throws Exception {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(LabyrinthMain.class.getResource(fxml)));
        Scene newScene = new Scene(pane, width, height);
        primaryStage.setScene(newScene);
    }

    public static void closeApplication() {
        primaryStage.close();
        Platform.exit();
    }
}
