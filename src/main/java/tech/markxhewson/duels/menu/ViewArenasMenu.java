package tech.markxhewson.duels.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.arena.Arena;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.ItemBuilder;

public class ViewArenasMenu {

    private final Duels plugin;

    private final ChestGui menu;

    public ViewArenasMenu(Duels plugin) {
        this.plugin = plugin;

        this.menu = new ChestGui(3, "ᴀʀᴇɴᴀs");
        menu.setOnGlobalClick(event -> event.setCancelled(true));

        updateItems();
    }

    public void open(Player player) {
        menu.show(player);
    }

    private void updateItems() {
        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").build()));
        background.setRepeat(true);

        menu.addPane(background);

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        int index = 1;

        for (Arena arena : plugin.getArenaManager().getArenas().values()) {
            if (arena.isInUse()) {
                DuelGame duelGame = plugin.getDuelGameManager().findGame(arena.getId());

                pane.addItem(new GuiItem(new ItemBuilder(Material.GRASS)
                        .setDisplayName(arena.getName())
                        .setLore(
                                "&esᴛᴀᴛᴇ: &c&lɪɴ ᴜsᴇ &7(" + duelGame.getPlayerOne().getName() + " &fᴠs &7" + duelGame.getPlayerTwo().getName() + ")",
                                "",
                                "&7ᴄʟɪᴄᴋ ᴛᴏ sᴘᴇᴄᴛᴀᴛᴇ."
                        )
                        .build(), event -> startSpectating((Player) event.getWhoClicked(), arena)
                ), index++, 1);
            } else {
                pane.addItem(new GuiItem(new ItemBuilder(Material.GRASS)
                        .setDisplayName(arena.getName())
                        .setLore(
                                "&esᴛᴀᴛᴇ: &a&lᴏᴘᴇɴ",
                                "",
                                "&7sᴛᴀʀᴛ ᴀ ᴅᴜᴇʟ ᴜsɪɴɢ /ᴅᴜᴇʟ <ᴘʟᴀʏᴇʀ>!"
                        )
                        .build()
                ), index++, 1);
            }

        }

        menu.addPane(pane);
    }

    public void startSpectating(Player player, Arena arena) {
        DuelGame duelGame = plugin.getDuelGameManager().findGame(arena.getId());

        if (plugin.getDuelGameManager().findGame(player.getUniqueId()) != null) {
            player.sendMessage(CC.translate("&c<!> ʏᴏᴜ ᴀʀᴇ ᴀʟʀᴇᴀᴅʏ ɪɴ ᴀ ᴅᴜᴇʟ!"));
            return;
        }

        duelGame.addSpectator(player);
    }

}
