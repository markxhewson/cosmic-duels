package tech.markxhewson.duels.manager.duel.setting;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum DuelSetting {
    GOLDEN_APPLES(Material.ENCHANTED_GOLDEN_APPLE, "ɢᴏʟᴅᴇɴ ᴀᴘᴘʟᴇs", true),
    MCMMO(Material.DIAMOND_AXE, "ᴍᴄᴍᴍᴏ", true),
    POTIONS(Material.BREWING_STAND, "ᴘᴏᴛɪᴏɴs", true),
    BOWS(Material.BOW, "ʙᴏᴡs", true),
    HEALING(Material.GHAST_TEAR, "ʜᴇᴀʟɪɴɢ", true),
    FOOD_LOSS(Material.COOKED_BEEF, "ғᴏᴏᴅ ʟᴏss", true),
    ENDER_PEARLS(Material.ENDER_PEARL, "ᴇɴᴅᴇʀ ᴘᴇᴀʀʟs", true),
    RISK_INVENTORY(Material.CHEST, "ʀɪsᴋ ɪɴᴠᴇɴᴛᴏʀʏ", false),
    ARMOR(Material.DIAMOND_CHESTPLATE, "ᴀʀᴍᴏʀ", true),
    WEAPONS(Material.DIAMOND_SWORD, "ᴡᴇᴀᴘᴏɴs", true),
    SLASHFIX(Material.ANVIL, "/ғɪx", true),
    SLASHFIX_ALL(Material.ANVIL, "/ғɪx ᴀʟʟ", true),
    SLASHFLY(Material.FEATHER, "/ғʟʏ", false),
    COSMIC_ENVOY(Material.FIREWORK_ROCKET, "ᴄᴏsᴍɪᴄ ᴇɴᴠᴏʏ &c(ᴄᴏᴍɪɴɢ sᴏᴏɴ)", false),
    DEATH_CERTIFICATES(Material.PAPER, "ᴅᴇᴀᴛʜ ᴄᴇʀᴛɪғɪᴄᴀᴛᴇs", true),
    INVENTORY_PETS(Material.GHAST_SPAWN_EGG, "ɪɴᴠᴇɴᴛᴏʀʏ ᴘᴇᴛs", false),
    ALPINE_CLIENT(Material.PAINTING, "ᴀʟᴘɪɴᴇ ᴄʟɪᴇɴᴛ", true),
    ITEM_SKINS(Material.ENDER_EYE, "ɪᴛᴇᴍ sᴋɪɴs &c(ᴄᴏᴍɪɴɢ sᴏᴏɴ)", true);

    private final Material material;
    private final String displayName;
    private final boolean defaultValue;

    DuelSetting(Material material, String displayName, boolean defaultValue) {
        this.material = material;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
    }
}
