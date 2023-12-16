package tech.markxhewson.duels.manager.arena;

import lombok.Getter;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.util.LocationUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ArenaManager {

    private final Duels plugin;
    private final Map<String, Arena> arenas = new ConcurrentHashMap<>();

    public ArenaManager(Duels plugin) {
        this.plugin = plugin;

        loadArenas();
    }

    private void loadArenas() {
        plugin.getConfig().getConfigurationSection("arenas").getKeys(false).forEach(arenaId -> {
            plugin.getLogger().info("Loading arena " + arenaId + "...");

            if (!plugin.getConfig().getBoolean("arenas." + arenaId + ".active")) {
                plugin.getLogger().info("Arena " + arenaId + " is not active, skipping...");
                return;
            }

            Arena arena = new Arena(
                    arenaId,
                    plugin.getConfig().getString("arenas." + arenaId + ".name"),
                    LocationUtil.deserializeLocation(plugin.getConfig().getString("arenas." + arenaId + ".spawnOne")),
                    LocationUtil.deserializeLocation(plugin.getConfig().getString("arenas." + arenaId + ".spawnTwo"))
            );

            addArena(arena);
        });
    }

    public Arena addArena(Arena arena) {
        return arenas.put(arena.getName(), arena);
    }

    public Arena getArena(String arenaId) {
        return arenas.get(arenaId);
    }
}
