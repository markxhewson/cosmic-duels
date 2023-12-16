package tech.markxhewson.duels.manager.duel.setting;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum DuelSetting {
    GOLDEN_APPLES(Material.ENCHANTED_GOLDEN_APPLE, "&e&lɢᴏʟᴅᴇɴ ᴀᴘᴘʟᴇs"),
    MCMMO(Material.DIAMOND_AXE, "&e&lᴍᴄᴍᴍᴏ"),
    POTIONS(Material.BREWING_STAND, "&e&lᴘᴏᴛɪᴏɴs"),
    BOWS(Material.BOW, "&e&lʙᴏᴡs"),
    HEALING(Material.GHAST_TEAR, "&e&lʜᴇᴀʟɪɴɢ"),
    FOOD_LOSS(Material.COOKED_BEEF, "&e&lғᴏᴏᴅ ʟᴏss"),
    ENDER_PEARLS(Material.ENDER_PEARL, "&e&lᴇɴᴅᴇʀ ᴘᴇᴀʀʟs"),
    RISK_INVENTORY(Material.CHEST, "&e&lʀɪsᴋ ɪɴᴠᴇɴᴛᴏʀʏ"),
    ARMOR(Material.DIAMOND_CHESTPLATE, "&e&lᴀʀᴍᴏʀ"),
    WEAPONS(Material.DIAMOND_SWORD, "&e&lᴡᴇᴀᴘᴏɴs"),
    SLASHFIX(Material.ANVIL, "&e&l/ғɪx"),
    SLASHFIX_ALL(Material.ANVIL, "&e&l/ғɪx ᴀʟʟ"),
    SLASHFLY(Material.FEATHER, "&e&l/ғʟʏ"),
    COSMIC_ENVOY(Material.FIREWORK_ROCKET, "&e&lᴄᴏsᴍɪᴄ ᴇɴᴠᴏʏ"),
    DEATH_CERTIFICATES(Material.PAPER, "&e&lᴅᴇᴀᴛʜ ᴄᴇʀᴛɪғɪᴄᴀᴛᴇs"),
    INVENTORY_PETS(Material.GHAST_SPAWN_EGG, "&e&lɪɴᴠᴇɴᴛᴏʀʏ ᴘᴇᴛs"),
    COSMIC_CLIENT(Material.PAINTING, "&e&lᴄᴏsᴍɪᴄ ᴄʟɪᴇɴᴛ"),
    ITEM_SKINS(Material.ENDER_EYE, "&e&lɪᴛᴇᴍ sᴋɪɴs");

    private final Material material;
    private final String displayName;

    DuelSetting(Material material, String displayName) {
        this.material = material;
        this.displayName = displayName;
    }
}
