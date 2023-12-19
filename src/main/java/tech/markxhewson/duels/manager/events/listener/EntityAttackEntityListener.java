package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;

public class EntityAttackEntityListener implements Listener {

    private final Duels plugin;

    public EntityAttackEntityListener(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (player.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            duelGame.setWinner(damager);
            duelGame.endGame();
        }
    }


}
