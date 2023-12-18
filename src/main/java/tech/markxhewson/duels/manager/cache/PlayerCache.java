package tech.markxhewson.duels.manager.cache;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
public class PlayerCache {

    private final UUID uuid;
    private ItemStack[] inventoryContents;
    private ItemStack[] armorContents;

}
