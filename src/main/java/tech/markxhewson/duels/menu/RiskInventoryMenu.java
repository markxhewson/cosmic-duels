package tech.markxhewson.duels.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.ItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiskInventoryMenu {

    private final Duels plugin;
    private final DuelGame duelGame;

    private final ChestGui menu;
    private final StaticPane pane;

    private final int[] playerOneSlots = { 1, 2, 3, 10, 11, 12, 19, 20, 21 };
    private final int[] playerTwoSlots = { 5, 6, 7, 14, 15, 16, 23, 24, 25 };
    private final List<ItemStack> playerOneItems = new ArrayList<>();
    private final List<ItemStack> playerTwoItems = new ArrayList<>();

    private boolean countdownActive = false;
    private int confirmCountdown = 5;
    private BukkitTask countdownTask;

    private boolean playerOneConfirmed = false;
    private boolean playerTwoConfirmed = false;

    public RiskInventoryMenu(Duels plugin, DuelGame duelGame) {
        this.plugin = plugin;
        this.duelGame = duelGame;

        this.menu = new ChestGui(3, duelGame.getPlayerOne().getName() + " ".repeat(10) + duelGame.getPlayerTwo().getName());
        this.pane = new StaticPane(0, 0, 9, 3);

        menu.setOnGlobalClick(event -> event.setCancelled(true));
        menu.setOnTopClick(this::removeItem);
        menu.setOnBottomClick(this::addItem);
        menu.setOnClose(event -> {
            plugin.getDuelGameManager().removeDuelGame(duelGame);
            menu.getViewers().forEach(HumanEntity::closeInventory);
        });

        updateItems();
    }

    public void open(Player player) {
        menu.show(player);
    }

    private void updateItems() {
        pane.addItem(new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(" ").build()), 0, 0);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 0, 1);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 0, 2);

        for (int i = 0; i < 3; i++) {
            pane.addItem(new GuiItem(new ItemBuilder(!countdownActive ? Material.GRAY_STAINED_GLASS_PANE : (confirmCountdown >= 4 ? Material.GREEN_STAINED_GLASS_PANE : (confirmCountdown >= 2 ? Material.YELLOW_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE)))
                    .setDisplayName(countdownActive ? "&6" + confirmCountdown + "s &7(Click to cancel)" : "&6Click to confirm")
                    .build(), event -> {
                confirmItems((Player) event.getWhoClicked());
            }), 4, i);
        }

        pane.addItem(new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(" ").build()), 8, 0);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 8, 1);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 8, 2);

        menu.addPane(pane);
    }

    public void addItem(InventoryClickEvent event) {
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

        if (player.getName().equals(duelGame.getPlayerOne().getName())) {
            playerOneItems.add(item);
        } else {
            playerTwoItems.add(item);
        }

        pane.addItem(new GuiItem(item), Slot.fromIndex(slot));
        player.getInventory().remove(item);

        menu.update();
    }

    public void removeItem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR || item.getType() == Material.GRAY_STAINED_GLASS_PANE || item.getType() == Material.RED_STAINED_GLASS_PANE) {
            return;
        }

        if (!doesSlotBelongToPlayer(player, event.getSlot())) {
            player.sendMessage(CC.translate("&cʏᴏᴜ ᴄᴀɴɴᴏᴛ ʀᴇᴍᴏᴠᴇ ᴀɴ ɪᴛᴇᴍ ғʀᴏᴍ ʏᴏᴜʀ ᴏᴡɴ ʀɪsᴋ ɪɴᴠᴇɴᴛᴏʀʏ."));
            return;
        }

        if (player.getName().equals(duelGame.getPlayerOne().getName())) {
            playerOneItems.remove(item);
        } else {
            playerTwoItems.remove(item);
        }

        pane.removeItem(Slot.fromIndex(event.getSlot()));
        player.getInventory().addItem(item);

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

    public boolean doesSlotBelongToPlayer(Player player, int index) {
        for (int slot : player.getName().equals(duelGame.getPlayerOne().getName()) ? playerOneSlots : playerTwoSlots) {
            if (slot == index) {
                return true;
            }
        }

        return false;
    }

    public void confirmItems(Player player) {
        if (player.getName().equals(duelGame.getPlayerOne().getName())) {
            playerOneConfirmed = true;
        } else {
            playerTwoConfirmed = true;
        }

        if (!playerOneConfirmed || !playerTwoConfirmed) {
            return;
        }

        if (this.countdownActive) {
            cancelCountdown();
            return;
        }

        this.countdownActive = true;
        updateMenu();

        this.countdownTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::handleCountdown, 0L, 20L);
    }

    public void handleCountdown() {
        if (this.confirmCountdown <= 0) {
            cancelCountdown();
            this.duelGame.startGame();
            saveItems();
        } else {
            this.countdownActive = true;
            this.confirmCountdown--;
        }

        updateMenu();
    }

    public void cancelCountdown() {
        this.countdownTask.cancel();
        this.countdownActive = false;
        this.confirmCountdown = 5;
        updateMenu();
    }

    public void updateMenu() {
        updateItems();
        menu.update();
    }

    public void saveItems() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.addAll(playerOneItems);
        rewards.addAll(playerTwoItems);

        plugin.getDuelRewardManager().addPendingReward(duelGame, rewards);
    }
}