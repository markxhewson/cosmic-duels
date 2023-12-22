package tech.markxhewson.duels.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.arena.Arena;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.util.CC;
import tech.markxhewson.duels.util.ItemBuilder;

@Getter
public class SelectArenaMenu {

    private final Duels plugin;
    private final DuelGame duelGame;

    private final ChestGui menu;

    public SelectArenaMenu(Duels plugin, DuelGame duelGame) {
        this.plugin = plugin;
        this.duelGame = duelGame;

        this.menu = new ChestGui(3, "ᴀʀᴇɴᴀ sᴇʟᴇᴄᴛɪᴏɴ");
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

                pane.addItem(new GuiItem(new ItemBuilder(Material.GRASS).setDisplayName(arena.getName())
                        .setLore(
                                "&esᴛᴀᴛᴇ: &c&lɪɴ ᴜsᴇ &7(" + duelGame.getPlayerOne().getName() + " &fᴠs &7" + duelGame.getPlayerTwo().getName() + ")"
                        )
                        .build()
                ), index++, 1);
            } else {
                pane.addItem(new GuiItem(new ItemBuilder(Material.GRASS).setDisplayName(arena.getName())
                        .setLore(
                                "&esᴛᴀᴛᴇ: &a&lᴏᴘᴇɴ"
                        )
                        .build(), event -> setArena(arena)
                ), index++, 1);
            }
        }
        menu.addPane(pane);
    }

    public void setArena(Arena arena) {
        if (arena.isInUse()) {
            this.duelGame.getPlayerOne().sendMessage(CC.translate("&c&l<!> &cᴛʜᴀᴛ ᴀʀᴇɴᴀ ɪs ɪɴ ᴜsᴇ!"));
            duelGame.getPlayerOne().closeInventory();
            return;
        }

        duelGame.setArena(arena);
        duelGame.getPlayerOne().closeInventory();
        this.duelGame.getPlayerOne().sendMessage(CC.translate("&e<!> sᴜᴄᴄᴇssғᴜʟʟʏ sᴇɴᴛ &f" + this.duelGame.getPlayerTwo().getName() + "&e ᴀ ᴅᴜᴇʟ ɪɴᴠɪᴛᴇ!"));
        this.plugin.getDuelGameManager().addInvite(this.duelGame);
    }

}

