package tech.markxhewson.duels.command;

import org.bukkit.World;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.util.CC;

public class EnvoyCommand {

    private final Duels plugin;

    public EnvoyCommand(Duels plugin) {
        this.plugin = plugin;
    }

    // used to manually place envoy locations in the different arenas, for debugging purposes
    @Command({"envoy"})
    @CommandPermission("duels.admin")
    public void onEnvoyDebugCommand(Player player) {
        plugin.envoyDebug = !plugin.envoyDebug;
        player.sendMessage(CC.translate("&aEnvoy debug mode toggled to &e" + plugin.envoyDebug + "&a."));
    }

}
