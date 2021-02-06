package cx.rain.mc.fabric.rainauth;

import cx.rain.mc.fabric.rainauth.command.Commands;
import cx.rain.mc.fabric.rainauth.data.ConfigManager;
import cx.rain.mc.fabric.rainauth.event.Events;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLeaveCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLoginCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerMoveCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class RainAuth implements ModInitializer {
    public static final String MODID = "RainAuth";

    private static RainAuth INSTANCE;

    private List<UUID> PlayerJoined = new ArrayList<>();

    private final Logger log = LogManager.getLogger(MODID);
    private final ConfigManager configManager = new ConfigManager();

    public RainAuth() {
        INSTANCE = this;
    }

    @Override
    public void onInitialize() {
        log.info("I am working.");
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
