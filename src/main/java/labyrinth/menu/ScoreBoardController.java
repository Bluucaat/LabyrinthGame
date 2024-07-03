package labyrinth.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import labyrinth.results.DataRepository;
import labyrinth.results.PlayerStats;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScoreBoardController implements Initializable {

    @FXML
    private TableView<PlayerStats> scoreboardTable;

    @FXML
    private TableColumn<PlayerStats, String> usernameColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> movesColumn;

    @FXML
    private Button backButton;

    private final ObservableList<PlayerStats> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTableColumns();
        loadPlayerData();
        initializeTableSorting();
    }

    private void configureTableColumns() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        movesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfMoves"));
    }

    private void loadPlayerData() {
        List<PlayerStats> playerDataList = DataRepository.read();
        if (playerDataList != null) {
            observableList.addAll(playerDataList);
            scoreboardTable.setItems(observableList);
        }
    }

    private void initializeTableSorting() {
        usernameColumn.setSortType(TableColumn.SortType.ASCENDING);
        movesColumn.setSortType(TableColumn.SortType.ASCENDING);
        scoreboardTable.getSortOrder().add(movesColumn);
        scoreboardTable.sort();
    }

    @FXML
    private void back() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            stage.setTitle("Labyrinth Puzzle");
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            Logger.warn("error" + e.getMessage());
        }
    }
}
