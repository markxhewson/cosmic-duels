package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;
import tech.markxhewson.duels.util.CC;

public class CommandBlockListener implements Listener {

    private final Duels plugin;

    public CommandBlockListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandBlock(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        DuelGame isSpectating = plugin.getDuelGameManager().isSpectating(player.getUniqueId());

        if (isSpectating != null) {
            if (!message.startsWith("/duel leave")) {
                player.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ᴄᴀɴɴᴏᴛ ᴜsᴇ ᴄᴏᴍᴍᴀɴᴅs ᴡʜɪʟᴇ sᴘᴇᴄᴛᴀᴛɪɴɢ! &7ʏᴏᴜ ᴍᴀʏ ᴏɴʟʏ ᴜsᴇ /ᴅᴜᴇʟ ʟᴇᴀᴠᴇ"));
                event.setCancelled(true);
                return;
            }
        }


        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (message.startsWith("/fly") && !duelGame.getSettings().isSettingEnabled(DuelSetting.SLASHFLY)) {
            player.sendMessage(CC.translate("&cᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ ɪs ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ."));
            event.setCancelled(true);
        }
        else if (message.startsWith("/fix") && !duelGame.getSettings().isSettingEnabled(DuelSetting.SLASHFIX)) {
            player.sendMessage(CC.translate("&cᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ ɪs ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ."));
            event.setCancelled(true);
        }
        else if (message.startsWith("/fix all") && !duelGame.getSettings().isSettingEnabled(DuelSetting.SLASHFIX_ALL)) {
            player.sendMessage(CC.translate("&cᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ ɪs ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ."));
            event.setCancelled(true);
        }
    }

}
