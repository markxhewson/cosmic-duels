package tech.markxhewson.duels.manager.events;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import tech.markxhewson.duels.Duels;
import tech.markxhewson.duels.manager.events.listener.*;

import java.util.LinkedList;
import java.util.List;

@Getter
public class EventsManager {

    private final Duels plugin;
    private final List<Listener> listeners = new LinkedList<>();

    public EventsManager(Duels plugin) {
        this.plugin = plugin;

        addListener(new PlayerInteractListener(plugin));
        addListener(new BlockListener(plugin));
        addListener(new FoodLossListener(plugin));
        addListener(new HealthRegainListener(plugin));
        addListener(new ItemConsumeListener(plugin));
        addListener(new ProjectileLaunchListener(plugin));

        loadListeners();
    }

    public void addListener(Listener listener) {
        getListeners().add(listener);
    }

    public void removeListener(Listener listener) {
        getListeners().remove(listener);
    }

    public void reloadListeners() {
        getListeners().forEach(HandlerList::unregisterAll);
        getListeners().forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void loadListeners() {
        getListeners().forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void unloadListeners() {
        getListeners().forEach(HandlerList::unregisterAll);
    }

}