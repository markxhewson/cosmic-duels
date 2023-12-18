package tech.markxhewson.duels.manager.cache;

import lombok.Getter;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerCacheManager {

    private final Duels plugin;
    private final Map<UUID, PlayerCache> playerCache = new HashMap<>();

    public PlayerCacheManager(Duels plugin) {
        this.plugin = plugin;
    }

}
