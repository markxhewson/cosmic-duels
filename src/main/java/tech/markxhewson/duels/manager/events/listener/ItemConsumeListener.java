package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;
import tech.markxhewson.duels.util.CC;

public class ItemConsumeListener implements Listener {

    private final Duels plugin;

    public ItemConsumeListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());
        ItemStack item = event.getItem();

        if (item.getType() == Material.GOLDEN_APPLE || item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            if (duelGame != null && !duelGame.getSettings().isSettingEnabled(DuelSetting.GOLDEN_APPLES)) {
                player.sendMessage(CC.translate("&c&l<!> &cɢᴏʟᴅᴇɴ ᴀᴘᴘʟᴇs ᴀʀᴇ ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ!"));
                event.setCancelled(true);
            }
        }
        else if (item.getType() == Material.POTION) {
            if (duelGame != null && !duelGame.getSettings().isSettingEnabled(DuelSetting.POTIONS)) {
                player.sendMessage(CC.translate("&c&l<!> &cᴘᴏᴛɪᴏɴs ᴀʀᴇ ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ!"));
                event.setCancelled(true);
            }
        }

    }

}
