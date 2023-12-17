package tech.markxhewson.duels.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.ItemBuilder;

public class RiskInventoryMenu {

    private final Duels plugin;
    private final DuelGame duelGame;

    private final ChestGui menu;
    private final StaticPane pane;

    private final int[] playerOneSlots = { 1, 2, 3, 10, 11, 12, 19, 20, 21 };
    private final int[] playerTwoSlots = { 5, 6, 7, 14, 15, 16, 23, 24, 25 };

    public RiskInventoryMenu(Duels plugin, DuelGame duelGame) {
        this.plugin = plugin;
        this.duelGame = duelGame;

        this.menu = new ChestGui(3, duelGame.getPlayerOne().getName() + " ".repeat(10) + duelGame.getPlayerTwo().getName());
        this.pane = new StaticPane(0, 0, 9, 3);
        menu.setOnGlobalClick(event -> event.setCancelled(true));
        menu.setOnBottomClick(this::handleItem);

        updateItems();
    }

    public void open(Player player) {
        menu.show(player);
    }

    private void updateItems() {
        pane.addItem(new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(" ").build()), 0, 0);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 0, 1);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 0, 2);

        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 4, 0);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 4, 1);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 4, 2);

        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 8, 0);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 8, 1);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 8, 2);

        menu.addPane(pane);
    }

    public void handleItem(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        int slot = (player.getName().equals(duelGame.getPlayerOne().getName()) ? getNextAvailableSlot(playerOneSlots) : getNextAvailableSlot(playerTwoSlots));

        if (slot == -1) {
            player.sendMessage(CC.translate("&cʏᴏᴜ ʜᴀᴠᴇ ɴᴏ ᴍᴏʀᴇ sᴘᴀᴄᴇ ɪɴ ʏᴏᴜʀ ʀɪsᴋ ɪɴᴠᴇɴᴛᴏʀʏ."));
            return;
        }

        pane.addItem(new GuiItem(item), Slot.fromIndex(slot));
        menu.update();
    }

    public int getNextAvailableSlot(int[] slots) {
        for (int slot : slots) {
            ItemStack item = menu.getInventory().getItem(slot);

            if (item == null || item.getType() == Material.AIR) {
                return slot;
            }
        }

        return -1;
    }
}