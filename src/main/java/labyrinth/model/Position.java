package labyrinth.model;

public record Position(int row, int col) {
    @Override
    public String toString() {
        return "Position(" +
                "row=" + row +
                " col=" + col +
                ')';
    }
}
