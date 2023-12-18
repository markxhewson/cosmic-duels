package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;

public class BlockListener implements Listener {

    private final Duels plugin;

    public BlockListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        event.setCancelled(true);
    }

}
