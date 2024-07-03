package labyrinth.game;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import labyrinth.menu.MenuController;
import labyrinth.model.Position;
import labyrinth.model.PuzzleState;
import labyrinth.model.Square;
import labyrinth.results.PlayerStats;
import labyrinth.results.StatsManager;
import labyrinth.util.BoardGameMoveSelector;
import labyrinth.util.EnumImageStorage;
import labyrinth.util.ImageStorage;
import org.tinylog.Logger;

import static labyrinth.game.PopupController.showPopup;

public class LabyrinthController {

    @FXML
    private GridPane board;

    private final PuzzleState model = new PuzzleState();

    private final BoardGameMoveSelector selector = new BoardGameMoveSelector(model);

    private final ImageStorage<Square> imageStorage = new EnumImageStorage<>(Square.class);

    private final StatsManager statsManager = StatsManager.getInstance();

    @FXML
    private void initialize() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                board.add(createSquare(i, j), j, i);
            }
        }
    }

    private StackPane createSquare(int i, int j) {
        StackPane square = new StackPane();
        setSquareStyle(square, i, j);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.imageProperty().bind(Bindings.createObjectBinding(
                () -> imageStorage.get(model.squareProperty(i, j).get()),
                model.squareProperty(i, j)
        ));
        square.getChildren().add(imageView);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void setSquareStyle(StackPane square, int i, int j) {
        Square currentSquare = model.getSquare(new Position(i, j));
        if (currentSquare == Square.WALL) {
            square.getStyleClass().add("wall");
        } else if ((i == 3 && j == 0) || (i == 10 && j == 13)) {
            square.getStyleClass().add("gates");
        } else {
            square.getStyleClass().add("square");
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        StackPane square = (StackPane) event.getSource();
        int row = GridPane.getRowIndex(square);
        int col = GridPane.getColumnIndex(square);
        Logger.info("Click on square (" + row + "," + col + ")");
        selector.select(new Position(row, col));

        if (selector.isReadyToMove()) {
            selector.makeMove();
            model.printLegalMoves();
            if(model.getLegalMoves().isEmpty() && !(model.isSolved())){
                showPopup(LabyrinthApplication.primaryStage, "You lost!");
            }
            if (model.isSolved()) {
                PlayerStats playerStats = new PlayerStats(MenuController.getUsername(), model.getStepCount());
                statsManager.addPlayer(playerStats);
                showPopup(LabyrinthApplication.primaryStage, "You won!");
                Logger.debug("Puzzle solved!");
            }
        }
    }
}
