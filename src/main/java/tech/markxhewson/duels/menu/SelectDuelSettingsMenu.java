package tech.markxhewson.duels.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.ItemBuilder;

@Getter
public class SelectDuelSettingsMenu {

    private final Duels plugin;
    private final DuelGame duelGame;

    private final ChestGui menu;

    public SelectDuelSettingsMenu(Duels plugin, DuelGame duelGame) {
        this.plugin = plugin;
        this.duelGame = duelGame;

        this.menu = new ChestGui(3, "Duel Settings");
        menu.setOnGlobalClick(event -> {
            menu.update();
            event.setCancelled(true);
        });

        updateItems();
    }

    public void open(Player player) {
        menu.show(player);
    }

    private void updateItems() {
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        addSettingItem(pane, DuelSetting.GOLDEN_APPLES, 0, 0);
        addSettingItem(pane, DuelSetting.MCMMO, 1, 0);
        addSettingItem(pane, DuelSetting.POTIONS, 2, 0);
        addSettingItem(pane, DuelSetting.BOWS, 3, 0);
        addSettingItem(pane, DuelSetting.HEALING, 4, 0);
        addSettingItem(pane, DuelSetting.FOOD_LOSS, 5, 0);
        addSettingItem(pane, DuelSetting.ENDER_PEARLS, 6, 0);
        addSettingItem(pane, DuelSetting.RISK_INVENTORY, 7, 0);
        addSettingItem(pane, DuelSetting.ARMOR, 8, 0);
        addSettingItem(pane, DuelSetting.WEAPONS, 0, 1);
        addSettingItem(pane, DuelSetting.SLASHFIX, 1, 1);
        addSettingItem(pane, DuelSetting.SLASHFIX_ALL, 2, 1);
        addSettingItem(pane, DuelSetting.SLASHFLY, 3, 1);
        addSettingItem(pane, DuelSetting.COSMIC_ENVOY, 4, 1);
        addSettingItem(pane, DuelSetting.DEATH_CERTIFICATES, 5, 1);
        addSettingItem(pane, DuelSetting.INVENTORY_PETS, 6, 1);
        addSettingItem(pane, DuelSetting.COSMIC_CLIENT, 7, 1);
        addSettingItem(pane, DuelSetting.ITEM_SKINS, 8, 1);

        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 1, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 2, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 3, 2);

        pane.addItem(new GuiItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .setDisplayName("&e&lᴄᴏɴғɪʀᴍ sᴇᴛᴛɪɴɢs")
                .setLore(
                        "",
                        addSettingLore(DuelSetting.GOLDEN_APPLES),
                        addSettingLore(DuelSetting.MCMMO),
                        addSettingLore(DuelSetting.POTIONS),
                        addSettingLore(DuelSetting.BOWS),
                        addSettingLore(DuelSetting.HEALING),
                        addSettingLore(DuelSetting.FOOD_LOSS),
                        addSettingLore(DuelSetting.ENDER_PEARLS),
                        addSettingLore(DuelSetting.RISK_INVENTORY),
                        addSettingLore(DuelSetting.ARMOR),
                        addSettingLore(DuelSetting.WEAPONS),
                        addSettingLore(DuelSetting.SLASHFIX),
                        addSettingLore(DuelSetting.SLASHFIX_ALL),
                        addSettingLore(DuelSetting.SLASHFLY),
                        addSettingLore(DuelSetting.COSMIC_ENVOY),
                        addSettingLore(DuelSetting.DEATH_CERTIFICATES),
                        addSettingLore(DuelSetting.INVENTORY_PETS),
                        addSettingLore(DuelSetting.COSMIC_CLIENT),
                        addSettingLore(DuelSetting.ITEM_SKINS),
                        "",
                        "&7ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ᴛᴏ ᴛʜᴇ ᴀʀᴇɴᴀ sᴇʟᴇᴄᴛɪᴏɴ."
                ).build(), event -> {
            confirmSettings();
        }), 4, 2);

        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 5, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 6, 2);
        pane.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()), 7, 2);

        menu.addPane(pane);
    }

    public String addSettingLore(DuelSetting setting) {
        return CC.translate(setting.getDisplayName() + ": " + (this.duelGame.getSettings().isSettingEnabled(setting) ? "&a&lᴏɴ" : "&c&lᴏғғ"));
    }

    public void addSettingItem(StaticPane pane, DuelSetting setting, int x, int y) {
        pane.addItem(new GuiItem(
                new ItemBuilder(setting.getMaterial())
                        .setDisplayName(setting.getDisplayName())
                        .setLore(this.duelGame.getSettings().getSetting(setting) ? "&a&lᴇɴᴀʙʟᴇᴅ" : "&c&lᴅɪsᴀʙʟᴇᴅ")
                        .build(),
                event -> {
                    toggleDuelSetting(setting);
                }
        ), x, y);
    }

    private void toggleDuelSetting(DuelSetting duelSetting) {
        this.duelGame.getSettings().setSetting(duelSetting, !this.duelGame.getSettings().isSettingEnabled(duelSetting));

        menu.getPanes().clear();
        this.updateItems();

        menu.update();
    }

    public void confirmSettings() {
        this.duelGame.openArenaSelectionMenu();
    }

}
