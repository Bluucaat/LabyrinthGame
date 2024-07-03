package labyrinth.util;


import javafx.scene.image.Image;
import labyrinth.model.Square;

import java.util.HashMap;
import java.util.Map;

public class EnumImageStorage<T extends Square> implements ImageStorage<T> {

    private final Map<T, Image> map = new HashMap<>();

    public EnumImageStorage(Class<T> enumClass) {
        var path = enumClass.getPackage().getName().replace(".", "/");
        var url = String.format("%s/circle.png", path);
        try {
            map.put((T) Square.CIRCLE, new Image(url));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Image get(T constant) {
        return map.get(constant);
    }

}