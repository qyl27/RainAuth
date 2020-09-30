package cx.rain.mc.fabric.rainauth;

import cx.rain.mc.fabric.rainauth.command.Commands;
import cx.rain.mc.fabric.rainauth.data.ConfigManager;
import cx.rain.mc.fabric.raincommons.event.callback.entity.player.PlayerJoinedCallback;
import cx.rain.mc.fabric.raincommons.event.callback.entity.player.PlayerLeavingCallback;
import cx.rain.mc.fabric.raincommons.event.callback.entity.player.PlayerMoveCallback;
import cx.rain.mc.fabric.raincommons.event.entity.player.PlayerJoinedEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class RainAuth implements ModInitializer {
    public static RainAuth INSTANCE;

    public List<UUID> PlayerLogged = new ArrayList<>();

    private final Logger log = LogManager.getLogger("RainAuth");
    private final ConfigManager configManager = new ConfigManager();

    @Override
    public void onInitialize() {
        log.info("Enabled.");

        INSTANCE = this;

        new Commands();

        registerEvents();
    }

    public ConfigManager getData() {
        return configManager;
    }

    public void registerEvents() {
        // Fixme
//        PlayerJoinedCallback.EVENT.register(event -> {
//            sendNotLoginMessage(event.player);
//            return ActionResult.PASS;
//        });

        PlayerLeavingCallback.EVENT.register(event -> {
            if (PlayerLogged.contains(event.player.getUuid())) {
                PlayerLogged.remove(event.player.getUuid());
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });

        PlayerMoveCallback.EVENT.register(event -> {
            if (!PlayerLogged.contains(event.entity.getUuid())) {
                //sendNotLoginMessage((PlayerEntity) event.entity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackBlockCallback.EVENT.register((playerEntity, world, hand, blockPos, direction) -> {
            if (!PlayerLogged.contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (!PlayerLogged.contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult) -> {
            if (!PlayerLogged.contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (!PlayerLogged.contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            if (!PlayerLogged.contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return TypedActionResult.fail(playerEntity.getStackInHand(hand));
            }
            return TypedActionResult.pass(playerEntity.getStackInHand(hand));
        });
    }

    private void sendNotLoginMessage(PlayerEntity player) {
        if (configManager.hasRegistered(player.getName().asString(), player.getUuid())) {
            player.sendMessage(new TranslatableText("message.rainauth.not_login")
                    .setStyle(Style.EMPTY.withColor(Formatting.RED)), false);
        } else {
            player.sendMessage(new TranslatableText("message.rainauth.not_register")
                    .setStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
        }
    }
}
