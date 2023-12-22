package tech.markxhewson.duels.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.arena.Arena;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.reward.DuelReward;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.ItemBuilder;

@Getter
public class DuelRewardsMenu {

    private final Duels plugin;
    private final DuelReward duelReward;

    private final ChestGui menu;
    private final StaticPane pane;

    public DuelRewardsMenu(Duels plugin, DuelReward duelReward) {
        this.plugin = plugin;
        this.duelReward = duelReward;

        this.menu = new ChestGui(3, "ʏᴏᴜʀ ᴅᴜᴇʟ ʀᴇᴡᴀʀᴅs");
        this.pane = new StaticPane(0, 0, 9, 3);
        menu.setOnGlobalClick(event -> event.setCancelled(true));

        updateItems();
    }

    public void open(Player player) {
        menu.show(player);
    }

    private void updateItems() {
        int index = 0;

        for (ItemStack item : duelReward.getItems()) {
            pane.addItem(new GuiItem(item), Slot.fromIndex(index));
            index++;
        }

        pane.addItem(
                new GuiItem(
                        new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                                .setDisplayName("&6Retrieve rewards")
                                .build(), event -> giveRewards((Player) event.getWhoClicked())
                ), 8, 2
        );

        menu.addPane(pane);
    }

    public void giveRewards(Player player) {
        for (ItemStack item : duelReward.getItems()) {
            player.getInventory().addItem(item);
        }

        player.closeInventory();
        plugin.getDuelRewardManager().removeReward(player.getUniqueId());
    }

}

