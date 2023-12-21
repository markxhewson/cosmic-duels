package tech.markxhewson.duels.manager.cache;

import lombok.Getter;
import org.bukkit.entity.Player;
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

    public void addPlayerCache(Player player) {
        PlayerCache cache = new PlayerCache(player.getUniqueId(), player.getLocation(), player.getInventory().getContents(), player.getInventory().getArmorContents());
        playerCache.put(player.getUniqueId(), cache);
    }

    public void removePlayerCache(UUID uuid) {
        playerCache.remove(uuid);
    }

    public void restorePlayer(Player player) {
        PlayerCache cache = playerCache.get(player.getUniqueId());
        player.teleport(cache.getLocation());
        player.getInventory().setContents(cache.getInventoryContents());
        player.getInventory().setArmorContents(cache.getArmorContents());
        player.setHealth(20);
        player.setFoodLevel(20);
        this.removePlayerCache(player.getUniqueId());
    }

}
