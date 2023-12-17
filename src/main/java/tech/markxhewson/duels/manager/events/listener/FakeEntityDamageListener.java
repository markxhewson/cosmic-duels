package tech.markxhewson.duels.manager.events.listener;

import com.gmail.nossr50.events.fake.FakeEntityDamageByEntityEvent;
import com.gmail.nossr50.events.fake.FakeEntityDamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;

public class FakeEntityDamageListener implements Listener {

    private final Duels plugin;

    public FakeEntityDamageListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFakeDamage(FakeEntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (!duelGame.getSettings().isSettingEnabled(DuelSetting.MCMMO)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFakeDamage(FakeEntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (!duelGame.getSettings().isSettingEnabled(DuelSetting.MCMMO)) {
            event.setCancelled(true);
        }
    }

}
