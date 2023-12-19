package tech.markxhewson.duels.command;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.game.DuelGame;
import tech.markxhewson.duels.manager.duel.util.Invite;
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
            playerOne.sendMessage(CC.translate("&c&l<!> &cʏᴏᴜ ᴄᴀɴɴᴏᴛ ᴅᴜᴇʟ ʏᴏᴜʀsᴇʟғ!"));
            return;
        }

        plugin.getDuelGameManager().createDuelGame(playerOne, playerTwo);
    }

    @Subcommand("accept")
    public void onDuelAcceptCommand(Player player) {
        Invite invite = plugin.getDuelGameManager().getInvite(player.getUniqueId());

        if (invite == null) {
            player.sendMessage(CC.translate("&c<!> ʏᴏᴜ ʜᴀᴠᴇ ɴᴏ ᴘᴇɴᴅɪɴɢ ᴅᴜᴇʟ ɪɴᴠɪᴛᴇs."));
            return;
        }

        ViewDuelSettingsMenu viewDuelSettingsMenu = new ViewDuelSettingsMenu(plugin, invite.getDuelGame());
        viewDuelSettingsMenu.open(player);

        plugin.getDuelGameManager().removeInvite(player.getUniqueId());
    }

    @Subcommand("rewards")
    public void onDuelRewardsCommand(Player player) {
        if (plugin.getDuelRewardManager().getReward(player.getUniqueId()) == null) {
            player.sendMessage(CC.translate("&c<!> ʏᴏᴜ ʜᴀᴠᴇ ɴᴏ ᴅᴜᴇʟ ʀᴇᴡᴀʀᴅs!"));
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

    @Subcommand("end")
    @CommandPermission("duels.admin")
    public void onDuelEndCommand(Player player) {
        DuelGame duelGame = plugin.getDuelGameManager().findGame(player.getUniqueId());

        if (duelGame == null) {
            player.sendMessage(CC.translate("&c<!> ʏᴏᴜ ᴀʀᴇ ɴᴏᴛ ɪɴ ᴀ ᴅᴜᴇʟ!"));
            return;
        }

        duelGame.announce(CC.translate("&c&l<!> &c" + player.getName() + " ʜᴀs ᴇɴᴅᴇᴅ ᴛʜᴇ ᴅᴜᴇʟ!"));
        duelGame.endGame();
    }

}
