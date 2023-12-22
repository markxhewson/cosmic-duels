package tech.markxhewson.duels.manager.reward;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Data
public class DuelReward {

    private final UUID gameUuid;
    private final List<ItemStack> items;

}
