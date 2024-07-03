package labyrinth.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import lombok.Getter;
import org.tinylog.Logger;
import puzzle.TwoPhaseMoveState;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents the state of a puzzle.
 * It implements the TwoPhaseMoveState interface with Position as the type of the moves.
 * The puzzle is represented as a 14x14 board of Squares, with a player at a certain Position on the board.
 */
public class PuzzleState implements TwoPhaseMoveState<Position> {


    public static final int BOARD_SIZE = 14;

    private final ReadOnlyObjectWrapper<Square>[][] board;
    @Getter
    private Position playerPosition;
    @Getter
    private int stepCount;

    /**
     * Constructs a new PuzzleState.
     * The board is initialized with all Squares set to NONE, except for the borders which are set to WALL.
     * The player is initially at the position (3, 0).
     */
    @SuppressWarnings("unchecked")
    public PuzzleState() {
        board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        playerPosition = new Position(3, 0);
    }

    /**
     * Initializes the board with all Squares set to NONE, except for the borders which are set to WALL.
     * Also, sets the initial walls and the player's initial position.
     */
    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(Square.NONE);
            }
        }
        setBorders();
        setInitialWalls();
    }

    /**
     * Sets the borders of the board to WALL.
     */
    private void setBorders() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i][0].set(Square.WALL);
            board[0][i].set(Square.WALL);
            board[i][BOARD_SIZE - 1].set(Square.WALL);
            board[BOARD_SIZE - 1][i].set(Square.WALL);
        }
    }

    /**
     * Sets the initial walls of the board.
     * Also, sets the player's initial position and the goal position.
     */
    private void setInitialWalls() {
        int[][] walls = {
                {1, 1}, {BOARD_SIZE - 2, 1}, {1, BOARD_SIZE - 2}, {BOARD_SIZE - 2, BOARD_SIZE - 2},
                {1, 7}, {2, 5}, {2, 7}, {2, 9}, {3, 3}, {3, 9},
                {4, 4}, {4, 10}, {5, 1}, {5, 6}, {5, 7}, {6, 6},
                {6, 9}, {6, 11}, {7, 3}, {8, 2}, {8, 5}, {8, 8},
                {8, 12}, {9, 7}, {10, 2}, {10, 3}, {10, 10}, {11, 4},
                {11, 6}, {11, 8}, {11, 9}
        };

        for (int[] wall : walls) {
            board[wall[0]][wall[1]].set(Square.WALL);
        }

        board[3][0].set(Square.CIRCLE);
        board[10][BOARD_SIZE - 1].set(Square.GOAL);
    }

    /**
     * Returns the ReadOnlyObjectProperty of the Square at the given position.
     * @param i The row of the position
     * @param j The column of the position
     * @return The ReadOnlyObjectProperty of the Square at the given position
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * Returns the Square at the given position.
     * @param p The position
     * @return The Square at the given position
     */
    public Square getSquare(Position p) {
        return board[p.row()][p.col()].get();
    }

    /**
     * Checks if the Square at the given position is NONE.
     * @param p The position
     * @return true if the Square at the given position is NONE, false otherwise
     */
    public boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }

    /**
     * Checks if the given position is on the board.
     * @param p The position
     * @return true if the position is on the board, false otherwise
     */
    public static boolean isOnBoard(Position p) {
        return 0 <= p.row() && p.row() < BOARD_SIZE && 0 <= p.col() && p.col() < BOARD_SIZE;
    }

    /**
     * Checks if it is legal to move from the given position.
     * It is legal to move from the position if it is the player's position.
     * @param position The position
     * @return true if it is legal to move from the position, false otherwise
     */
    @Override
    public boolean isLegalToMoveFrom(Position position) {
        return position.equals(playerPosition);
    }

    /**
     * Checks if the puzzle is solved.
     * The puzzle is solved if the player is at the goal position.
     * @return true if the puzzle is solved, false otherwise
     */
    @Override
    public boolean isSolved() {
        return getSquare(new Position(10, BOARD_SIZE - 1)) == Square.CIRCLE;
    }

    /**
     * Checks if the given move is legal.
     * A move is legal if it is a valid move, both the from and to positions are on the board, the from position is the player's position,
     * the to position is either NONE or the goal, and the to position is not a WALL.
     * @param move The move
     * @return true if the move is legal, false otherwise
     */
    @Override
    public boolean isLegalMove(TwoPhaseMove<Position> move) {
        int dx = move.to().row() - move.from().row();
        int dy = move.to().col() - move.from().col();

        boolean isValidMove = (dx == 0 && dy == 1) || (dx == 1 && dy == 0);
        boolean isOnBoard = isOnBoard(move.from()) && isOnBoard(move.to());
        boolean isFromValidSquare = getSquare(move.from()) == Square.CIRCLE;
        boolean isToSquareValid = isEmpty(move.to()) || getSquare(move.to()) == Square.GOAL;
        boolean isToSquareNotWall = getSquare(move.to()) != Square.WALL;
        return isValidMove && isOnBoard && isFromValidSquare && isToSquareValid && isToSquareNotWall;
    }

    /**
     * Makes the given move.
     * The Square at the from position is set to NONE, the Square at the to position is set to CIRCLE, and the player's position is updated.
     * @param move The move
     */
    @Override
    public void makeMove(TwoPhaseMove<Position> move) {
        board[move.from().row()][move.from().col()].set(Square.NONE);
        board[move.to().row()][move.to().col()].set(Square.CIRCLE);
        this.stepCount++;
        System.out.println(stepCount);
        playerPosition = move.to();
    }

    /**
     * Returns the set of legal moves.
     * A move is legal if it is a valid move, both the from and to positions are on the board, the from position is the player's position,
     * the to position is either NONE or the goal, and the to position is not a WALL.
     * @return The set of legal moves
     */
    @Override
    public Set<TwoPhaseMove<Position>> getLegalMoves() {
        Set<TwoPhaseMove<Position>> legalMoves = new HashSet<>();
        Position from = playerPosition;

        int[][] directions = {{0, 1}, {1, 0}};
        for (int[] direction : directions) {
            Position to = new Position(from.row() + direction[0], from.col() + direction[1]);
            if (isOnBoard(to) && isLegalMove(new TwoPhaseMove<>(from, to))) {
                legalMoves.add(new TwoPhaseMove<>(from, to));
            }
        }
        return legalMoves;
    }

    public void printLegalMoves() {
        Logger.info("Legal moves:");
        for (TwoPhaseMove<Position> move : getLegalMoves()) {
            Logger.info("From: " + move.from() + " To: " + move.to());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PuzzleState that = (PuzzleState) obj;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!Objects.equals(board[i][j].get(), that.board[i][j].get())) {
                    return false;
                }
            }
        }
        return playerPosition.equals(that.playerPosition);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(playerPosition);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                result = 31 * result + Objects.hashCode(board[i][j].get());
            }
        }
        return result;
    }

    @Override
    public PuzzleState clone() {
        PuzzleState clonedState = new PuzzleState();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                clonedState.board[i][j].set(this.board[i][j].get());
            }
        }
        clonedState.playerPosition = this.playerPosition;
        return clonedState;
    }

    /**
     * Returns a string representation of the PuzzleState.
     * The string representation is a 14x14 grid of numbers, where each number is the ordinal of the Square at that position on the board.
     * @return A string representation of the PuzzleState
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var model = new PuzzleState();
    }
}
