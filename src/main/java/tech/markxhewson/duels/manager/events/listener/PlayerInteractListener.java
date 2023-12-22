package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.LocationUtil;

import java.util.List;

public class PlayerInteractListener implements Listener {

    private final Duels plugin;

    public PlayerInteractListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getDebugPlayers().contains(player.getUniqueId())) {
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = player.getInventory().getItemInMainHand();
            Block block = event.getClickedBlock();

            if (block == null) {
                return;
            }

            if (item.getType() == Material.BLAZE_ROD && plugin.isEnvoyDebug()) {
                addEnvoyLocation(block);
                player.sendMessage(CC.translate("&a&l(!) &aᴇɴᴠᴏʏ ʟᴏᴄᴀᴛɪᴏɴ ᴀᴅᴅᴇᴅ ғᴏʀ " + plugin.getDebugArenaName() + "."));
                return;
            }

            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = player.getInventory().getItemInMainHand();

            switch (item.getType()) {
                case DIAMOND -> {
                    Location location = player.getLocation();
                    plugin.getConfig().set("arenas." + plugin.getDebugArenaName() + ".spawnOne", LocationUtil.serializeLocation(location));
                    player.sendMessage(CC.translate("&a&l(!) &asᴘᴀᴡɴ ᴏɴᴇ sᴇᴛ ғᴏʀ " + plugin.getDebugArenaName() + "."));
                }
                case EMERALD -> {
                    Location location = player.getLocation();
                    plugin.getConfig().set("arenas." + plugin.getDebugArenaName() + ".spawnTwo", LocationUtil.serializeLocation(location));
                    player.sendMessage(CC.translate("&a&l(!) &asᴘᴀᴡɴ ᴛᴡᴏ sᴇᴛ ғᴏʀ " + plugin.getDebugArenaName() + "."));
                }
                case NETHER_STAR -> {
                    Location location = player.getLocation();
                    plugin.getConfig().set("arenas." + plugin.getDebugArenaName() + ".center", LocationUtil.serializeLocation(location));
                    player.sendMessage(CC.translate("&a&l(!) &acᴇɴᴛᴇʀ sᴇᴛ ғᴏʀ " + plugin.getDebugArenaName() + "."));
                }
                default -> {
                    player.sendMessage(CC.translate("&c&l(!) &cYou must be holding a diamond &7(spawnOne)&c, emerald &7(spawnTwo)&c, or nether star &7(center) &cto use this command."));
                }
            }

            plugin.saveConfig();
        }
    }

    public void addEnvoyLocation(Block block) {
        List<String> locations = plugin.getConfig().getStringList("arenas." + plugin.getDebugArenaName() + ".envoyLocations");
        locations.add(LocationUtil.serializeLocation(block.getLocation()));

        plugin.getConfig().set("arenas." + plugin.getDebugArenaName() + ".envoyLocations", locations);
        plugin.saveConfig();
    }

}
