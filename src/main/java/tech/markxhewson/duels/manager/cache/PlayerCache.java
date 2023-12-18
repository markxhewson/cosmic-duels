package tech.markxhewson.duels.manager.cache;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
public class PlayerCache {

    private final UUID uuid;
    private final Location location;
    private ItemStack[] inventoryContents;
    private ItemStack[] armorContents;

    public PlayerCache(UUID uuid, Location location, ItemStack[] inventoryContents, ItemStack[] armorContents) {
        this.uuid = uuid;
        this.location = location;
        this.inventoryContents = inventoryContents;
        this.armorContents = armorContents;
    }

}
