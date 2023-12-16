package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;
import tech.markxhewson.duels.util.CC;

public class ProjectileLaunchListener implements Listener {

    private final Duels plugin;

    public ProjectileLaunchListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnderpearlThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getType() != EntityType.ENDER_PEARL || event.getEntity().getType() == EntityType.ARROW) {
            return;
        }

        if (!(event.getEntity().getShooter() instanceof Player player)) {
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (event.getEntity().getType() == EntityType.ENDER_PEARL) {
            if (duelGame != null && !duelGame.getSettings().isSettingEnabled(DuelSetting.ENDER_PEARLS)) {
                player.sendMessage(CC.translate("&c&l<!> &cᴇɴᴅᴇʀᴘᴇᴀʀʟs ᴀʀᴇ ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ!"));
                event.setCancelled(true);
            }
        }
        else if (event.getEntity().getType() == EntityType.ARROW) {
            if (duelGame != null && !duelGame.getSettings().isSettingEnabled(DuelSetting.BOWS)) {
                player.sendMessage(CC.translate("&c&l<!> &cʙᴏᴡs ᴀʀᴇ ᴅɪsᴀʙʟᴇᴅ ɪɴ ᴛʜɪs ᴅᴜᴇʟ!"));
                event.setCancelled(true);
            }
        }
    }

}
