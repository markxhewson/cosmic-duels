package tech.markxhewson.duels.manager.duel.setting;

import java.util.HashMap;
import java.util.Map;

public class DuelSettings {
    private Map<DuelSetting, Boolean> settings = new HashMap<>();

    public DuelSettings() {
        for (DuelSetting setting : DuelSetting.values()) {
            settings.put(setting, true);
        }
    }

    public boolean isSettingEnabled(DuelSetting setting) {
        return settings.getOrDefault(setting, false);
    }

    public void setSetting(DuelSetting setting, boolean value) {
        settings.put(setting, value);
    }

    public boolean getSetting(DuelSetting setting) {
        return settings.get(setting);
    }
}