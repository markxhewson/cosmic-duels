package tech.markxhewson.duels.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.util.Invite;
import tech.markxhewson.duels.manager.reward.DuelReward;
import tech.markxhewson.duels.menu.DuelRewardsMenu;
import tech.markxhewson.duels.menu.RiskInventoryMenu;
import tech.markxhewson.duels.menu.ViewArenasMenu;
import tech.markxhewson.duels.menu.ViewDuelSettingsMenu;
import tech.markxhewson.duels.util.CC;

@Command("duel")
public class DuelCommand {

    private final Duels plugin;

    public DuelCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @DefaultFor("duel")
    public void onDuelCommand(Player playerOne, Player playerTwo) {
        if (playerOne == playerTwo) {
            playerOne.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ᴄᴀɴɴᴏᴛ ᴅᴜᴇʟ ʏᴏᴜʀsᴇʟғ!"));
            return;
        }

        DuelGame duelGame = plugin.getDuelGameManager().findGame(playerOne.getUniqueId());

        if (duelGame != null) {
            playerOne.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ᴀʀᴇ ᴀʟʀᴇᴀᴅʏ ɪɴ ᴀ ᴅᴜᴇʟ!"));
            return;
        }

        DuelReward duelReward = plugin.getDuelRewardManager().getReward(playerOne.getUniqueId());

        if (duelReward != null) {
            playerOne.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ᴄᴀɴɴᴏᴛ sᴛᴀʀᴛ ᴀ ᴅᴜᴇʟ ʙᴇғᴏʀᴇ ᴇᴍᴘᴛʏɪɴɢ ʏᴏᴜʀ ᴅᴜᴇʟ ʀᴇᴡᴀʀᴅs ᴍᴇɴᴜ!"));
            return;
        }

        plugin.getDuelGameManager().createDuelGame(playerOne, playerTwo);
    }

    @Subcommand("accept")
    public void onDuelAcceptCommand(Player player) {
        Invite invite = plugin.getDuelGameManager().getInvite(player.getUniqueId());

        if (invite == null) {
            player.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ʜᴀᴠᴇ ɴᴏ ᴘᴇɴᴅɪɴɢ ᴅᴜᴇʟ ɪɴᴠɪᴛᴇs."));
            return;
        }

        ViewDuelSettingsMenu viewDuelSettingsMenu = new ViewDuelSettingsMenu(plugin, invite.getDuelGame());
        viewDuelSettingsMenu.open(player);

        plugin.getDuelGameManager().removeInvite(player.getUniqueId());
    }

    @Subcommand("rewards")
    public void onDuelRewardsCommand(Player player) {
        if (plugin.getDuelRewardManager().getReward(player.getUniqueId()) == null) {
            player.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ʜᴀᴠᴇ ɴᴏ ᴅᴜᴇʟ ʀᴇᴡᴀʀᴅs!"));
            return;
        }

        DuelRewardsMenu duelRewardsMenu = new DuelRewardsMenu(plugin, plugin.getDuelRewardManager().getReward(player.getUniqueId()));
        duelRewardsMenu.open(player);
    }

    @Subcommand("spectate")
    public void onDuelSpectate(Player player) {
        ViewArenasMenu viewArenasMenu = new ViewArenasMenu(plugin);
        viewArenasMenu.open(player);
    }

    @Subcommand("leave")
    public void onDuelLeave(Player player) {
        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            DuelGame isSpectating = plugin.getDuelGameManager().isSpectating(player.getUniqueId());

            if (isSpectating == null) {
                player.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ᴀʀᴇ ɴᴏᴛ sᴘᴇᴄᴛᴀᴛɪɴɢ ᴀ ᴅᴜᴇʟ!"));
                return;
            }

            World world = Bukkit.getWorld(plugin.getConfig().getString("defaultWorld"));

            if (world == null) {
                return;
            }

            isSpectating.removeSpectator(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(world.getSpawnLocation());
        }
    }

    @Subcommand("end")
    @CommandPermission("duels.admin")
    public void onDuelEndCommand(Player player) {
        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            player.sendMessage(CC.translate("&c&l(!) &cʏᴏᴜ ᴀʀᴇ ɴᴏᴛ ɪɴ ᴀ ᴅᴜᴇʟ!"));
            return;
        }

        duelGame.announce(CC.translate("&c&l(!) &c" + player.getName() + " ʜᴀs ᴇɴᴅᴇᴅ ᴛʜᴇ ᴅᴜᴇʟ!"));
        duelGame.endGame();
    }

    @Subcommand("debug")
    @CommandPermission("duels.admin")
    public void onDuelDebugCommand(Player player) {
        if (plugin.getDebugPlayers().contains(player.getUniqueId())) {
            plugin.getDebugPlayers().remove(player.getUniqueId());
            player.sendMessage(CC.translate("&c&l(!) &cᴅᴇʙᴜɢ ᴍᴏᴅᴇ ᴅɪsᴀʙʟᴇᴅ!"));
        } else {
            plugin.getDebugPlayers().add(player.getUniqueId());
            player.sendMessage(CC.translate("&a&l(!) &aᴅᴇʙᴜɢ ᴍᴏᴅᴇ ᴇɴᴀʙʟᴇᴅ!"));
        }
    }

    @Subcommand("admin envoy")
    @CommandPermission("duels.admin")
    public void onDuelDebugEnvoyCommand(Player player) {
        if (plugin.isEnvoyDebug()) {
            plugin.setEnvoyDebug(false);
            player.sendMessage(CC.translate("&c&l(!) &cᴇɴᴠᴏʏ ᴅᴇʙᴜɢ ᴍᴏᴅᴇ ᴅɪsᴀʙʟᴇᴅ!"));
        } else {
            plugin.setEnvoyDebug(true);
            player.sendMessage(CC.translate("&a&l(!) &aᴇɴᴠᴏʏ ᴅᴇʙᴜɢ ᴍᴏᴅᴇ ᴇɴᴀʙʟᴇᴅ!"));
        }
    }

    @Subcommand("admin arena")
    @CommandPermission("duels.admin")
    public void changeDebugArena(Player player, String arenaName) {
        if (plugin.getConfig().getConfigurationSection("arenas." + arenaName) == null) {
            player.sendMessage(CC.translate("&c&l(!) &cᴛʜᴀᴛ ᴀʀᴇɴᴀ ᴅᴏᴇs ɴᴏᴛ ᴇxɪsᴛ!"));
            return;
        }

        plugin.setDebugArenaName(arenaName);
        player.sendMessage(CC.translate("&a&l(!) &aᴅᴇʙᴜɢ ᴀʀᴇɴᴀ ᴄʜᴀɴɢᴇᴅ ᴛᴏ &e" + arenaName + "&a!"));
    }

}
