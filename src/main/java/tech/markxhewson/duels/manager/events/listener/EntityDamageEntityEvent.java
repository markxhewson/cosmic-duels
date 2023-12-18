package tech.markxhewson.duels.manager.events.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.util.CC;

public class EntityDamageEntityEvent implements Listener {

    private final Duels plugin;

    public EntityDamageEntityEvent(Duels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttack(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Player player)) {
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            return;
        }

        if (duelGame.getGraceTime() > 0) {
            event.setCancelled(true);
        }
    }

}
