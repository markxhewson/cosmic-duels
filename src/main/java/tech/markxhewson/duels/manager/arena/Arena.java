package tech.markxhewson.duels.manager.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class Arena {

    private final String id;
    private final String name;
    private final Location spawnOne;
    private final Location spawnTwo;
    private boolean inUse = false;

    public Arena(String id, String name, Location spawnOne, Location spawnTwo) {
        this.id = id;
        this.name = name;
        this.spawnOne = spawnOne;
        this.spawnTwo = spawnTwo;
    }

}
