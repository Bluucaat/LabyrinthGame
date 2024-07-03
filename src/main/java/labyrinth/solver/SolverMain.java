package labyrinth.solver;

import labyrinth.model.Position;
import labyrinth.model.PuzzleState;
import puzzle.TwoPhaseMoveState;
import puzzle.solver.BreadthFirstSearch;

public class SolverMain {
    public static void main(String[] args) {
        var bfs = new BreadthFirstSearch<TwoPhaseMoveState.TwoPhaseMove<Position>>();
        bfs.solveAndPrintSolution(new PuzzleState());
    }
}
