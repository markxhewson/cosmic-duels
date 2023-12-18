package tech.markxhewson.duels.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ViewDuelSettingsMenu {

    private final Duels plugin;
    private final DuelGame duelGame;

    private final ChestGui menu;

    public ViewDuelSettingsMenu(Duels plugin, DuelGame duelGame) {
        this.plugin = plugin;
        this.duelGame = duelGame;

        this.menu = new ChestGui(3, "Duel Settings");
        menu.setOnGlobalClick(event -> event.setCancelled(true));

        updateItems();
    }

    public void open(Player player) {
        menu.show(player);
    }

    private void updateItems() {
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        int index = 0;

        for (DuelSetting value : DuelSetting.values()) {
            addSettingItem(pane, value, index);
            index++;
        }

        List<String> confirmLore = new ArrayList<>();
        confirmLore.add("");

        for (DuelSetting value : DuelSetting.values()) {
            confirmLore.add(addSettingLore(value));
        }

        confirmLore.add("");
        confirmLore.add("&7&7ᴄʟɪᴄᴋ ᴛᴏ ᴀᴄᴄᴇᴘᴛ ᴅᴜᴇʟ.");

        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 1, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 2, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 3, 2);

        pane.addItem(new GuiItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .setDisplayName("&e&lᴄᴏɴғɪʀᴍ sᴇᴛᴛɪɴɢs")
                .setLore(confirmLore).build(), event -> confirmSettings()), 4, 2);

        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 5, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 6, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 7, 2);

        menu.addPane(pane);
    }

    public String addSettingLore(DuelSetting setting) {
        return CC.translate("&e" + setting.getDisplayName() + ": " + (this.duelGame.getSettings().isSettingEnabled(setting) ? "&aᴏɴ" : "&cᴏғғ"));
    }

    public void addSettingItem(StaticPane pane, DuelSetting setting, int index) {
        pane.addItem(new GuiItem(
                new ItemBuilder(setting.getMaterial())
                        .setDisplayName("&e&l" + setting.getDisplayName())
                        .setLore(this.duelGame.getSettings().getSetting(setting) ? "&a&lᴇɴᴀʙʟᴇᴅ" : "&c&lᴅɪsᴀʙʟᴇᴅ")
                        .build()
        ), Slot.fromIndex(index));
    }

    public void confirmSettings() {
        if (this.duelGame.getSettings().isSettingEnabled(DuelSetting.RISK_INVENTORY)) {
            this.duelGame.openRiskInventoryMenu();
        } else {
            this.duelGame.startGame();
        }
    }

}

