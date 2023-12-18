package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;

public class FoodLossListener implements Listener {

    private final Duels plugin;

    public FoodLossListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodLoss(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (!duelGame.getSettings().isSettingEnabled(DuelSetting.FOOD_LOSS)) {
            event.setCancelled(true);
        }
    }

}
