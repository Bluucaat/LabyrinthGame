package labyrinth.results;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.tinylog.Logger;

public class DataRepository {


    static ObjectMapper objectMapper = new ObjectMapper();


    public static void write(HashSet<PlayerStats> playerData) {
        List<PlayerStats> existingData = read();
        existingData.addAll(playerData);

        try {
            objectMapper.writeValue(new File("result.json"), existingData);
        } catch (IOException e) {
            Logger.error("Error writing to file: " + e.getMessage());
        }
    }

    public static List<PlayerStats> read() {
        File file = new File("result.json");
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, PlayerStats.class));
        } catch (IOException e) {
            Logger.error("Error reading from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
