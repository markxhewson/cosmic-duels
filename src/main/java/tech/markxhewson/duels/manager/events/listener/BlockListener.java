package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import tech.markxhewson.duels.Duels;
public class BlockListener implements Listener {

    private final Duels plugin;

    public BlockListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (plugin.getDuelGameManager().findGame(player.getUniqueId()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (plugin.getDuelGameManager().findGame(player.getUniqueId()) != null) {
            event.setCancelled(true);
        }
    }

}
