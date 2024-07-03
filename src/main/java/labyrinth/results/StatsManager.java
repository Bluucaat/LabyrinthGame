package labyrinth.results;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StatsManager {

    @Getter
    private static HashSet<PlayerStats> players;

    private StatsManager() {
        players = new HashSet<>();
    }

    private static final class InstanceHolder {
        private static final StatsManager instance = new StatsManager();
    }

    public static StatsManager getInstance() {
        return InstanceHolder.instance;
    }

    public void addPlayer(PlayerStats player) {
        players.add(player);
    }

    public void writePlayers() {
        DataRepository.write(players);
    }

}
