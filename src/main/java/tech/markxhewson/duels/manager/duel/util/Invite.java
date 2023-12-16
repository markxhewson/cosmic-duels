package tech.markxhewson.duels.manager.duel.util;

import lombok.Data;
import org.bukkit.entity.Player;
import tech.markxhewson.duels.manager.duel.game.DuelGame;

@Data
public class Invite {

    private final long timeSent;
    private final long timeExpires;
    private final Player sender;
    private final Player receiver;
    private final DuelGame duelGame;

}
