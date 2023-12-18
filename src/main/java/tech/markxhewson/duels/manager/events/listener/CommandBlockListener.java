package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.Bukkit;
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
        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (event.getMessage().startsWith("/fly") && !duelGame.getSettings().isSettingEnabled(DuelSetting.SLASHFLY)) {
            player.sendMessage(CC.translate("&cᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ ɪs ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ."));
            event.setCancelled(true);
        }
        else if (event.getMessage().startsWith("/fix") && !duelGame.getSettings().isSettingEnabled(DuelSetting.SLASHFIX)) {
            player.sendMessage(CC.translate("&cᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ ɪs ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ."));
            event.setCancelled(true);
        }
        else if (event.getMessage().startsWith("/fix all") && !duelGame.getSettings().isSettingEnabled(DuelSetting.SLASHFIX_ALL)) {
            player.sendMessage(CC.translate("&cᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ ɪs ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ."));
            event.setCancelled(true);
        }
    }

}
