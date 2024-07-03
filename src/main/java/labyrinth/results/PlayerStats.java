package labyrinth.results;

import lombok.Getter;

@Getter
public class PlayerStats {
    private String playerName = "None";
    private int numberOfMoves;


    public PlayerStats(){
    }
    public PlayerStats(String playerName, int numberOfMoves) {
        this.playerName = playerName;
        this.numberOfMoves = numberOfMoves;
    }

    @Override
    public String toString() {
        return "Data{" + "PlayerName='" + playerName + "'" +
                ", numberOfMoves=" + numberOfMoves +
                "}";
    }
}
