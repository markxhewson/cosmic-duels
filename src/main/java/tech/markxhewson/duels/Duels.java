package tech.markxhewson.duels;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import tech.markxhewson.duels.command.DuelCommand;
import tech.markxhewson.duels.command.WorldCommand;
import tech.markxhewson.duels.manager.events.EventsManager;
import tech.markxhewson.duels.manager.arena.ArenaManager;
import tech.markxhewson.duels.manager.duel.game.DuelGameManager;
import tech.markxhewson.duels.manager.reward.DuelRewardManager;
import tech.markxhewson.duels.manager.world.ArenaWorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public final class Duels extends JavaPlugin {

    @Getter
    private static Duels instance;
    private BukkitCommandHandler commandHandler;
    private EventsManager eventsManager;

    private DuelGameManager duelGameManager;
    private DuelRewardManager duelRewardManager;
    private ArenaManager arenaManager;
    private ArenaWorldManager arenaWorldManager;

    private List<UUID> debugPlayers = new ArrayList<>();
    private String debugArenaName = "grasslands";
    public boolean envoyDebug = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;

        commandHandler = BukkitCommandHandler.create(this);
        eventsManager = new EventsManager(this);
        loadCommands();

        duelGameManager = new DuelGameManager(this);
        duelRewardManager = new DuelRewardManager(this);
        arenaWorldManager = new ArenaWorldManager(this);
        arenaManager = new ArenaManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    void loadCommands() {
        commandHandler.register(new WorldCommand(this));
        commandHandler.register(new DuelCommand(this));
    }

}
