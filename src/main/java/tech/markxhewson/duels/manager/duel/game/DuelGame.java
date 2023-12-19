package tech.markxhewson.duels.manager.duel.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.arena.Arena;
import tech.markxhewson.duels.manager.duel.setting.DuelSetting;
import tech.markxhewson.duels.manager.duel.setting.DuelSettings;
import tech.markxhewson.duels.manager.duel.state.GameState;
import tech.markxhewson.duels.manager.reward.DuelReward;
import tech.markxhewson.duels.menu.RiskInventoryMenu;
import tech.markxhewson.duels.menu.SelectArenaMenu;
import tech.markxhewson.duels.menu.SelectDuelSettingsMenu;
import tech.markxhewson.duels.util.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DuelGame {

    private final Duels plugin;
    private final UUID gameUUID = UUID.randomUUID();

    // player one is always the one who initiated the duel
    private final Player playerOne;
    private final Player playerTwo;
    private Player winner;

    private final List<Player> spectators = new ArrayList<>();

    private final DuelSettings settings = new DuelSettings();
    private Arena arena;

    private int graceTime = 10;
    private final int maxTime = 900_000; // 15 minutes in millis

    private int gameTickTaskId;
    private long startTime;
    private GameState gameState = GameState.SETTING_UP;

    public DuelGame(Duels plugin, Player playerOne, Player playerTwo) {
        this.plugin = plugin;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        openSettingsMenu();
    }

    public void addSpectator(Player player) {
        this.spectators.add(player);

        plugin.getDuelGameManager().getPlayerCacheManager().addPlayerCache(player);

        player.teleport(this.getPlayerOne().getLocation());
        player.setGameMode(GameMode.SPECTATOR);
    }

    public void openRiskInventoryMenu() {
        RiskInventoryMenu riskInventoryMenu = new RiskInventoryMenu(plugin, this);

        riskInventoryMenu.open(playerOne);
        riskInventoryMenu.open(playerTwo);
    }

    public void openSettingsMenu() {
        SelectDuelSettingsMenu selectDuelSettingsMenu = new SelectDuelSettingsMenu(plugin, this);
        selectDuelSettingsMenu.open(playerOne);
    }

    public void openArenaSelectionMenu() {
        SelectArenaMenu selectArenaMenu = new SelectArenaMenu(plugin, this);
        selectArenaMenu.open(playerOne);
    }

    public List<Player> getPlayers() {
        return List.of(this.getPlayerOne(), this.getPlayerTwo());
    }

    public void startGame() {
        getPlayers().forEach(player -> {
            plugin.getDuelGameManager().getPlayerCacheManager().addPlayerCache(player);

            player.setHealth(20);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setGameMode(GameMode.SURVIVAL);

            player.sendTitle(CC.translate("&c&lᴅᴜᴇʟ"), CC.translate("&e&l" + this.getPlayerOne().getName() + " &eᴠs &e&l" + this.getPlayerTwo().getName()));
        });

        this.getPlayerOne().teleport(this.getArena().getSpawnOne());
        this.getPlayerTwo().teleport(this.getArena().getSpawnTwo());

        this.announce("&e&l<!> &eᴛʜᴇ ᴅᴜᴇʟ ᴡɪʟʟ ʙᴇɢɪɴ sʜᴏʀᴛʟʏ...");
        setGameState(GameState.STARTING);
    }

    public long getGameTime() {
        return System.currentTimeMillis() - startTime;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        switch (gameState) {
            case STARTING -> {
                BukkitTask gameTickTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::gameLoop, 0L, 20L);
                this.gameTickTaskId = gameTickTask.getTaskId();
                this.startTime = System.currentTimeMillis();
                this.arena.setInUse(true);
            }
            case PLAYING -> {
                this.announce("&e&l<!> &eᴛʜᴇ ᴅᴜᴇʟ ʜᴀs sᴛᴀʀᴛᴇᴅ!");
            }
            case ENDED -> {
                this.getPlayers().forEach(player -> plugin.getDuelGameManager().getPlayerCacheManager().restorePlayer(player));
                this.getSpectators().forEach(player -> plugin.getDuelGameManager().getPlayerCacheManager().restorePlayer(player));
                this.arena.setInUse(false);
                this.plugin.getDuelGameManager().removeDuelGame(this);
            }
        }
    }

    public void gameLoop() {
        switch (this.getGameState()) {
            case STARTING -> {
                if (this.getGraceTime() <= 0) {
                    setGameState(GameState.PLAYING);
                    return;
                }

                this.announce("&e&l<!> &eᴛʜᴇ ᴅᴜᴇʟ ᴡɪʟʟ ʙᴇɢɪɴ ɪɴ &c" + this.getGraceTime() + " &esᴇᴄᴏɴᴅs...");
                this.setGraceTime(this.getGraceTime() - 1);
            }
            case PLAYING -> {
                long difference = getGameTime();

                if (difference >= getMaxTime()) {
                    announce("&cᴅᴜᴇʟ ʜᴀs ᴇɴᴅᴇᴅ! ᴍᴀᴛᴄʜ ʟᴀsᴛᴇᴅ ᴛᴏᴏ ʟᴏɴɢ.");
                    endGame();
                }
            }
        }
    }

    public void setWinner(Player player) {
        plugin.getDuelRewardManager().addReward(this.getGameUUID(), player.getUniqueId());
        this.winner = player;

        if (getSettings().isSettingEnabled(DuelSetting.RISK_INVENTORY)) {
            player.sendMessage(CC.translate("&e&l<!> &eʏᴏᴜ ʜᴀᴠᴇ ᴡᴏɴ ᴛʜᴇ ᴅᴜᴇʟ, ʏᴏᴜ ʜᴀᴠᴇ ʀᴇᴡᴀʀᴅs ɪɴ /ᴅᴜᴇʟ ʀᴇᴡᴀʀᴅs!"));
        } else {
            player.sendMessage("&e&l<!> &eʏᴏᴜ ʜᴀᴠᴇ ᴡᴏɴ ᴛʜᴇ ᴅᴜᴇʟ!");
        }
    }

    public void endGame() {
        this.setGameState(GameState.ENDED);
    }

    public void announce(String message) {
        List<Player> players = List.of(this.getPlayerOne(), this.getPlayerTwo());

        players.forEach(player -> {
            player.sendMessage(CC.translate(message));
        });
    }
}
