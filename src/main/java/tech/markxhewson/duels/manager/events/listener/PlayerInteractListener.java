package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.util.LocationUtil;

import java.util.List;

public class PlayerInteractListener implements Listener {

    private final Duels plugin;

    public PlayerInteractListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND && plugin.envoyDebug) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();

            if (block == null) {
                return;
            }

            List<String> locations = plugin.getConfig().getStringList("arenas." + plugin.envoyArenaName + ".envoyLocations");
            locations.add(LocationUtil.serializeLocation(block.getLocation()));

            plugin.getConfig().set("arenas." + plugin.envoyArenaName + ".envoyLocations", locations);
            plugin.saveConfig();

            player.sendMessage("Added envoy location for arena " + plugin.envoyArenaName + " at " + LocationUtil.serializeLocation(block.getLocation()));
        }
    }

}
