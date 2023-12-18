package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;

public class HealthRegainListener implements Listener {

    private final Duels plugin;

    public HealthRegainListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHealthRegain(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (!duelGame.getSettings().isSettingEnabled(DuelSetting.HEALING)) {
            event.setCancelled(true);
        }
    }

}
