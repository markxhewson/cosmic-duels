package tech.markxhewson.duels.manager.duel.game;

import org.bukkit.entity.Player;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.util.Invite;
import tech.markxhewson.duels.util.CC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DuelGameManager {

    private final Duels plugin;
    private final List<DuelGame> duelGames = new LinkedList<>();
    private final Map<UUID, Invite> duelInvites = new ConcurrentHashMap<>();

    public DuelGameManager(Duels plugin) {
        this.plugin = plugin;

        // check for expired invites every 5s
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::checkExpiredInvites, 0L, 100L);
    }

    public void createDuelGame(Player playerOne, Player playerTwo) {
        DuelGame duelGame = new DuelGame(plugin, playerOne, playerTwo);
        duelGames.add(duelGame);
    }

    public void removeDuelGame(DuelGame duelGame) {
        duelGames.remove(duelGame);
    }

    // find by user uuid
    public DuelGame findGame(UUID uuid) {
        return duelGames.stream().filter(duelGame -> duelGame.getPlayerOne().getUniqueId().equals(uuid) || duelGame.getPlayerTwo().getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    // find by arena id
    public DuelGame findGame(String arenaId) {
        return duelGames.stream().filter(duelGame -> duelGame.getArena().getId().equals(arenaId)).findFirst().orElse(null);
    }

    public void addInvite(DuelGame duelGame) {
        Invite invite = new Invite(
                System.currentTimeMillis(),
                System.currentTimeMillis() + 15000, // 15s
                duelGame.getPlayerOne(),
                duelGame.getPlayerTwo(),
                duelGame
        );

        duelInvites.put(duelGame.getPlayerTwo().getUniqueId(), invite);
        duelGame.getPlayerTwo().sendMessage(CC.translate("&e<!> " + duelGame.getPlayerOne().getName() + " ʜᴀs sᴇɴᴛ ʏᴏᴜ ᴀ ᴅᴜᴇʟ ɪɴᴠɪᴛᴇ! &7(/ᴅᴜᴇʟ ᴀᴄᴄᴇᴘᴛ " + duelGame.getPlayerOne().getName() + ")"));
    }

    public void removeInvite(UUID uuid) {
        duelInvites.remove(uuid);
    }

    public Invite getInvite(UUID uuid) {
        return duelInvites.getOrDefault(uuid, null);
    }

    public void checkExpiredInvites() {
        duelInvites.forEach((uuid, invite) -> {
            if (System.currentTimeMillis() > invite.getTimeExpires()) {
                Player player = plugin.getServer().getPlayer(uuid);
                if (player != null) {
                    player.sendMessage(CC.translate("&c<!> ʏᴏᴜʀ ᴅᴜᴇʟ ɪɴᴠɪᴛᴇ ғʀᴏᴍ &f" + invite.getSender().getName() + " &cʜᴀs ᴇxᴘɪʀᴇᴅ."));
                }
                duelInvites.remove(uuid);
            }
        });
    }

}
