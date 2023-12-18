package tech.markxhewson.duels.manager.duel.game;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.cache.PlayerCacheManager;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.util.Invite;
import tech.markxhewson.duels.util.CC;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class DuelGameManager {

    private final Duels plugin;
    private final PlayerCacheManager playerCacheManager;

    private final List<DuelGame> duelGames = new LinkedList<>();
    private final Map<UUID, Invite> duelInvites = new ConcurrentHashMap<>();

    public DuelGameManager(Duels plugin) {
        this.plugin = plugin;
        this.playerCacheManager = new PlayerCacheManager(plugin);

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
        return duelGames.stream().filter(duelGame -> duelGame.getPlayers().stream().map(Entity::getUniqueId).toList().contains(uuid)).findFirst().orElse(null);
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
        Iterator<Map.Entry<UUID, Invite>> iterator = duelInvites.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, Invite> entry = iterator.next();
            UUID uuid = entry.getKey();
            Invite invite = entry.getValue();

            if (System.currentTimeMillis() > invite.getTimeExpires()) {
                Player player = plugin.getServer().getPlayer(uuid);
                if (player != null) {
                    player.sendMessage(CC.translate("&c<!> ʏᴏᴜʀ ᴅᴜᴇʟ ɪɴᴠɪᴛᴇ ғʀᴏᴍ &f" + invite.getSender().getName() + " &cʜᴀs ᴇxᴘɪʀᴇᴅ."));
                }

                iterator.remove();
            }
        }
    }


}
