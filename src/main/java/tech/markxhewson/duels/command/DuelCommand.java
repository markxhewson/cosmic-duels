package tech.markxhewson.duels.command;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.duel.util.Invite;
import tech.markxhewson.duels.menu.RiskInventoryMenu;
import tech.markxhewson.duels.menu.ViewDuelSettingsMenu;
import tech.markxhewson.duels.util.CC;

@Command("duel")
@CommandPermission("duels.player.duel")
public class DuelCommand {

    private final Duels plugin;

    public DuelCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @DefaultFor("duel")
    public void onDuelCommand(Player playerOne, Player playerTwo) {
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

}
