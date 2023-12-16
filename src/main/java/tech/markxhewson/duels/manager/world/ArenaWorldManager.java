package tech.markxhewson.duels.manager.world;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import tech.markxhewson.duels.Duels;

@Getter
public class ArenaWorldManager {

    private final Duels plugin;

    private final World world;
    private final String worldName = "arenas";

    public ArenaWorldManager(Duels plugin) {
        this.plugin = plugin;

        this.world = loadWorld();
    }

    public World loadWorld() {
        WorldCreator worldCreator = new WorldCreator(worldName)
                .environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .generateStructures(false)
                .generator(new VoidWorldGenerator()
                );

        return worldCreator.createWorld();
    }

}
