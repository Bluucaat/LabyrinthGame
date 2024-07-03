package labyrinth.model;

import org.junit.jupiter.api.Test;
import puzzle.TwoPhaseMoveState;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleStateTest {

    @Test
    void squareProperty() {
        PuzzleState puzzleState = new PuzzleState();
        assertNotNull(puzzleState.squareProperty(3, 3));
        assertEquals(Square.NONE, puzzleState.squareProperty(1, 2).get());
    }

    @Test
    void getSquare() {
        PuzzleState puzzleState = new PuzzleState();
        assertEquals(Square.WALL, puzzleState.getSquare(new Position(0, 0)));
        assertEquals(Square.NONE, puzzleState.getSquare(new Position(1, 2)));
        assertEquals(Square.CIRCLE, puzzleState.getSquare(new Position(3, 0)));
    }

    @Test
    void isEmpty() {
        PuzzleState puzzleState = new PuzzleState();
        assertFalse(puzzleState.isEmpty(new Position(0, 0)));
        assertFalse(puzzleState.isEmpty(new Position(1, 1)));
        assertFalse(puzzleState.isEmpty(new Position(3, 0)));
    }

    @Test
    void isOnBoard() {
        assertTrue(PuzzleState.isOnBoard(new Position(0, 0)));
        assertFalse(PuzzleState.isOnBoard(new Position(-1, 0)));
        assertFalse(PuzzleState.isOnBoard(new Position(0, 15)));
    }

    @Test
    void isLegalToMoveFrom() {
        PuzzleState puzzleState = new PuzzleState();
        assertTrue(puzzleState.isLegalToMoveFrom(new Position(3, 0)));
        assertFalse(puzzleState.isLegalToMoveFrom(new Position(1, 1)));
    }

    @Test
    void isSolved() {
        PuzzleState puzzleState = new PuzzleState();
        assertFalse(puzzleState.isSolved());
        puzzleState.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(10, PuzzleState.BOARD_SIZE - 1)));
        assertTrue(puzzleState.isSolved());
    }

    @Test
    void isLegalMove() {
        PuzzleState puzzleState = new PuzzleState();
        assertTrue(puzzleState.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(3, 1))));
        assertFalse(puzzleState.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(2, 0))));
    }

    @Test
    void makeMove() {
        PuzzleState puzzleState = new PuzzleState();
        assertEquals(Square.CIRCLE, puzzleState.getSquare(new Position(3, 0)));
        puzzleState.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(3, 1)));
        assertEquals(Square.NONE, puzzleState.getSquare(new Position(3, 0)));
        assertEquals(Square.CIRCLE, puzzleState.getSquare(new Position(3, 1)));
    }

    @Test
    void getLegalMoves() {
        PuzzleState puzzleState = new PuzzleState();
        Set<TwoPhaseMoveState.TwoPhaseMove<Position>> legalMoves = puzzleState.getLegalMoves();
        assertEquals(1, legalMoves.size());
        puzzleState.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(3, 1)));
        legalMoves = puzzleState.getLegalMoves();
        assertEquals(2, legalMoves.size());

        // Adding a test for position (7, 2)
        puzzleState = new PuzzleState();
        puzzleState.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(7, 2)));
        legalMoves = puzzleState.getLegalMoves();
        assertEquals(0, legalMoves.size());
    }


    @Test
    void testEquals() {
        PuzzleState puzzleState1 = new PuzzleState();
        PuzzleState puzzleState2 = new PuzzleState();
        assertEquals(puzzleState1, puzzleState2);
        puzzleState2.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(3, 1)));
        assertNotEquals(puzzleState1, puzzleState2);
    }

    @Test
    void testHashCode() {
        PuzzleState puzzleState1 = new PuzzleState();
        PuzzleState puzzleState2 = new PuzzleState();
        assertEquals(puzzleState1.hashCode(), puzzleState2.hashCode());
        puzzleState2.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(3, 0), new Position(3, 1)));
        assertNotEquals(puzzleState1.hashCode(), puzzleState2.hashCode());
    }

    @Test
    void testClone() {
        PuzzleState puzzleState1 = new PuzzleState();
        PuzzleState puzzleState2 = puzzleState1.clone();
        assertEquals(puzzleState1, puzzleState2);
        assertNotSame(puzzleState1, puzzleState2);
    }
}
