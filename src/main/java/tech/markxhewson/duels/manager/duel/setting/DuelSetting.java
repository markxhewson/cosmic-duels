package tech.markxhewson.duels.manager.duel.setting;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum DuelSetting {
    GOLDEN_APPLES(Material.ENCHANTED_GOLDEN_APPLE, "ɢᴏʟᴅᴇɴ ᴀᴘᴘʟᴇs"),
    MCMMO(Material.DIAMOND_AXE, "ᴍᴄᴍᴍᴏ"),
    POTIONS(Material.BREWING_STAND, "ᴘᴏᴛɪᴏɴs"),
    BOWS(Material.BOW, "ʙᴏᴡs"),
    HEALING(Material.GHAST_TEAR, "ʜᴇᴀʟɪɴɢ"),
    FOOD_LOSS(Material.COOKED_BEEF, "ғᴏᴏᴅ ʟᴏss"),
    ENDER_PEARLS(Material.ENDER_PEARL, "ᴇɴᴅᴇʀ ᴘᴇᴀʀʟs"),
    RISK_INVENTORY(Material.CHEST, "ʀɪsᴋ ɪɴᴠᴇɴᴛᴏʀʏ"),
    ARMOR(Material.DIAMOND_CHESTPLATE, "ᴀʀᴍᴏʀ"),
    WEAPONS(Material.DIAMOND_SWORD, "ᴡᴇᴀᴘᴏɴs"),
    SLASHFIX(Material.ANVIL, "/ғɪx"),
    SLASHFIX_ALL(Material.ANVIL, "/ғɪx ᴀʟʟ"),
    SLASHFLY(Material.FEATHER, "/ғʟʏ"),
    COSMIC_ENVOY(Material.FIREWORK_ROCKET, "ᴄᴏsᴍɪᴄ ᴇɴᴠᴏʏ"),
    DEATH_CERTIFICATES(Material.PAPER, "ᴅᴇᴀᴛʜ ᴄᴇʀᴛɪғɪᴄᴀᴛᴇs"),
    INVENTORY_PETS(Material.GHAST_SPAWN_EGG, "ɪɴᴠᴇɴᴛᴏʀʏ ᴘᴇᴛs"),
    COSMIC_CLIENT(Material.PAINTING, "ᴄᴏsᴍɪᴄ ᴄʟɪᴇɴᴛ"),
    ITEM_SKINS(Material.ENDER_EYE, "ɪᴛᴇᴍ sᴋɪɴs");

    private final Material material;
    private final String displayName;

    DuelSetting(Material material, String displayName) {
        this.material = material;
        this.displayName = displayName;
    }
}
