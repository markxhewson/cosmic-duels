package tech.markxhewson.duels;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import tech.markxhewson.duels.command.ArenaCommand;
import tech.markxhewson.duels.command.DuelCommand;
import tech.markxhewson.duels.command.EnvoyCommand;
import tech.markxhewson.duels.command.WorldCommand;
import tech.markxhewson.duels.manager.events.EventsManager;
import tech.markxhewson.duels.manager.events.listener.PlayerInteractListener;
import tech.markxhewson.duels.manager.arena.ArenaManager;
import tech.markxhewson.duels.manager.duel.game.DuelGameManager;
import tech.markxhewson.duels.manager.world.ArenaWorldManager;

@Getter
public final class Duels extends JavaPlugin {

    @Getter
    private static Duels instance;
    private BukkitCommandHandler commandHandler;
    private EventsManager eventsManager;

    private DuelGameManager duelGameManager;
    private ArenaManager arenaManager;
    private ArenaWorldManager arenaWorldManager;

    public boolean envoyDebug = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;

        commandHandler = BukkitCommandHandler.create(this);
        eventsManager = new EventsManager(this);
        loadCommands();

        arenaWorldManager = new ArenaWorldManager(this);
        duelGameManager = new DuelGameManager(this);
        arenaManager = new ArenaManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    void loadCommands() {
        commandHandler.register(new WorldCommand(this));
        commandHandler.register(new EnvoyCommand(this));
        commandHandler.register(new DuelCommand(this));
        commandHandler.register(new ArenaCommand(this));
    }

}
