package cx.rain.mc.fabric.rainauth;

import cx.rain.mc.fabric.rainauth.command.Commands;
import cx.rain.mc.fabric.rainauth.data.ConfigManager;
import cx.rain.mc.fabric.rainauth.data.DataManager;
import cx.rain.mc.fabric.rainauth.event.Events;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public final class RainAuth implements ModInitializer {
    public static final String MODID = "rainauth";
    public static final String NAME = "RainAuth";

    private static RainAuth INSTANCE;

    private Map<UUID, Boolean> players = new HashMap<>();

    private final Logger log = LogManager.getLogger(NAME);
    private final DataManager data = new DataManager();
    private final ConfigManager configManager = new ConfigManager();

    public RainAuth() {
        INSTANCE = this;
    }

    @Override
    public void onInitialize() {
        log.info("Loading...");
        new Commands();
        new Events();
        log.info("Load finished.");
    }

    public static RainAuth getInstance() {
        return INSTANCE;
    }

    public DataManager getData() {
        return data;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Map<UUID, Boolean> getPlayers() {
        return players;
    }
}
