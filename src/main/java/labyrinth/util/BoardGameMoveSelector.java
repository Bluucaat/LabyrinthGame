package labyrinth.util;

import javafx.beans.property.ReadOnlyObjectWrapper;
import labyrinth.model.Position;
import labyrinth.model.PuzzleState;
import lombok.Getter;
import org.tinylog.Logger;
import puzzle.TwoPhaseMoveState;

public class BoardGameMoveSelector {

    public enum Phase {
        SELECT_FROM,
        SELECT_TO,
        READY_TO_MOVE
    }

    private final PuzzleState model;
    private final ReadOnlyObjectWrapper<Phase> phase = new ReadOnlyObjectWrapper<>(Phase.SELECT_FROM);
    @Getter
    private boolean invalidSelection = false;
    private Position from;
    private Position to;

    public BoardGameMoveSelector(PuzzleState model) {
        this.model = model;
    }

    public boolean isReadyToMove() {
        return phase.get() == Phase.READY_TO_MOVE;
    }

    public void select(Position position) {
        switch (phase.get()) {
            case SELECT_FROM -> selectFrom(position);
            case SELECT_TO -> selectTo(position);
            case READY_TO_MOVE -> throw new IllegalStateException();
        }
    }

    private void selectFrom(Position position) {
        if (position.equals(model.getPlayerPosition())) {
            from = position;
            phase.set(Phase.SELECT_TO);
            invalidSelection = false;
        } else {
            invalidSelection = true;
            Logger.warn("There is no player in the selected 'from' field");
        }
    }

    private void selectTo(Position position) {
        if (model.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(from, position))) {
            to = position;
            phase.set(Phase.READY_TO_MOVE);
            invalidSelection = false;
        } else {
            invalidSelection = true;
            Logger.warn("Illegal move");
        }
    }

    public void makeMove() {
        if (phase.get() != Phase.READY_TO_MOVE) {
            throw new IllegalStateException();
        }
        model.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(from, to));
        reset();
    }

    public void reset() {
        from = null;
        to = null;
        phase.set(Phase.SELECT_FROM);
        invalidSelection = false;
    }
}
