package tech.markxhewson.duels.command;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.menu.ViewArenasMenu;

@Command("arena")
@CommandPermission("duels.player.arena")
public class ArenaCommand {

    private final Duels plugin;

    public ArenaCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @DefaultFor("arena")
    public void onDuelCommand(Player playerOne) {
        ViewArenasMenu viewArenasMenu = new ViewArenasMenu(plugin);
        viewArenasMenu.open(playerOne);
    }

}
