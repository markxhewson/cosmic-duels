package tech.markxhewson.duels.manager.reward;

import org.bukkit.inventory.ItemStack;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DuelRewardManager {

    private final Duels plugin;
    private final Map<UUID, DuelReward> pendingDuelRewards = new HashMap<>(); // identified by duel game uuid
    private final Map<UUID, DuelReward> duelRewards = new HashMap<>(); // identified by winner uuid

    public DuelRewardManager(Duels plugin) {
        this.plugin = plugin;
    }

    public void addPendingReward(DuelGame duelGame, List<ItemStack> items) {
        DuelReward reward = new DuelReward(duelGame.getGameUUID(), items);
        pendingDuelRewards.put(duelGame.getGameUUID(), reward);
    }

    public void addReward(UUID gameUUID, UUID winnerUuid) {
        DuelReward reward = pendingDuelRewards.get(gameUUID);
        duelRewards.put(winnerUuid, reward);
        removePendingReward(gameUUID);
    }

    public DuelReward getReward(UUID winnerUuid) {
        return duelRewards.get(winnerUuid);
    }

    public void removeReward(UUID winnerUuid) {
        duelRewards.remove(winnerUuid);
    }

    public void removePendingReward(UUID gameUuid) {
        pendingDuelRewards.remove(gameUuid);
    }


}
