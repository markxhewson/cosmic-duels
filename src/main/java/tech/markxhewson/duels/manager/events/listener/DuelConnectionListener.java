package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;

public class DuelConnectionListener implements Listener {

    private final Duels plugin;

    public DuelConnectionListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        World playerWorld = player.getWorld();
        World arenaWorld = plugin.getArenaWorldManager().getWorld();

        if (arenaWorld == null) {
            return;
        }

        if (playerWorld.getName().equals(arenaWorld.getName())){
            DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

            if (duelGame != null) {
                return;
            }

            World defaultWorld = plugin.getServer().getWorld(plugin.getConfig().getString("defaultWorld"));

            if (defaultWorld == null) {
                return;
            }

            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(defaultWorld.getSpawnLocation());
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        DuelGame duelGame = plugin.getDuelGameManager().findGame(event.getPlayer().getUniqueId());

        if (duelGame == null) {
            Player player = event.getPlayer();
            DuelGame isSpecating = plugin.getDuelGameManager().isSpectating(event.getPlayer().getUniqueId());

            if (isSpecating == null) {
                return;
            }

            isSpecating.removeSpectator(event.getPlayer());
            player.setGameMode(GameMode.SURVIVAL);
            return;
        }

        duelGame.endGame();
    }

}
