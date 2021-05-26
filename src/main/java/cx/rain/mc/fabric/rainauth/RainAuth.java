package cx.rain.mc.fabric.rainauth;

import cx.rain.mc.fabric.rainauth.command.Commands;
import cx.rain.mc.fabric.rainauth.data.ConfigManager;
import cx.rain.mc.fabric.rainauth.event.Events;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class RainAuth implements ModInitializer {
    public static final String MODID = "rainauth";

    private static RainAuth INSTANCE;

    private List<UUID> PlayerJoined = new ArrayList<>();

    private final Logger log = LogManager.getLogger(MODID);
    private final ConfigManager configManager = new ConfigManager();

    public RainAuth() {
        INSTANCE = this;
    }

    @Override
    public void onInitialize() {
        log.info("Loading...");
        new Commands();
        new Events();
    }

    public static RainAuth getInstance() {
        return INSTANCE;
    }

    public ConfigManager getData() {
        return configManager;
    }

    public List<UUID> getPlayerJoined() {
        return PlayerJoined;
    }
}
