package tech.markxhewson.duels.command;

import org.bukkit.World;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.util.CC;

public class WorldCommand {

    private final Duels plugin;

    public WorldCommand(Duels plugin) {
        this.plugin = plugin;
    }

    // used to easily get to arena world for modifications
    @Command({"world"})
    @CommandPermission("duels.admin.world")
    public void onWorldCommand(Player player, String worldName) {
        World world = plugin.getServer().getWorld(worldName);

        if (world == null) {
            player.sendMessage(CC.translate("&cWorld not found!"));
            return;
        }

        player.sendMessage(CC.translate("&aTeleporting to world &e" + worldName + "&a..."));
        player.teleport(world.getSpawnLocation());
    }

}
