package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.block.Block;
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
            Block block = event.getClickedBlock();

            List<String> locations = plugin.getConfig().getStringList("arenas.grasslands.envoyLocations");
            locations.add(LocationUtil.serializeLocation(block.getLocation()));

            plugin.getConfig().set("arenas.grasslands.envoyLocations", locations);
            plugin.saveConfig();
            event.getPlayer().sendMessage("Added envoy location at " + LocationUtil.serializeLocation(block.getLocation()));
        }
    }

}
