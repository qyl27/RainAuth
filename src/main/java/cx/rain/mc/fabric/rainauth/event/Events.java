package cx.rain.mc.fabric.rainauth.event;

import cx.rain.mc.fabric.rainauth.RainAuth;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLeaveCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLoginCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerMoveCallback;
import cx.rain.mc.fabric.rainauth.networking.Networking;
import cx.rain.mc.fabric.rainauth.utility.TranslatableLanguage;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Events {
    private Map<UUID, Integer> notLoginMessageDelay = new HashMap<>();

    public Events() {
        PlayerLoginCallback.EVENT.register(((connection, player) -> {
            UUID uuid = player.getUuid();
            if (RainAuth.getInstance().getPlayers().containsKey(uuid)) {
                return ActionResult.FAIL;
            }
            RainAuth.getInstance().getPlayers().put(uuid, false);

//            Networking.openAuthGui(player);

            return ActionResult.PASS;
        }));

        PlayerLeaveCallback.EVENT.register(((player) -> {
            UUID uuid = player.getUuid();
            RainAuth.getInstance().getPlayers().remove(uuid);
        }));

        PlayerMoveCallback.EVENT.register(((player, world, direction) -> {
            if (!RainAuth.getInstance().getPlayers().get(player.getUuid())) {
                player.setPos(player.prevX, player.prevY, player.prevZ);
                sendNotLoginMessage((ServerPlayerEntity) player);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }));

        AttackBlockCallback.EVENT.register((player, world, hand, blockPos, direction) -> {
            if (!world.isClient) {
                if (!RainAuth.getInstance().getPlayers().get(player.getUuid())) {
                    sendNotLoginMessage((ServerPlayerEntity) player);
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if (!world.isClient) {
                if (!RainAuth.getInstance().getPlayers().get(player.getUuid())) {
                    sendNotLoginMessage((ServerPlayerEntity) player);
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });

        UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
            if (!world.isClient) {
                if (!RainAuth.getInstance().getPlayers().get(player.getUuid())) {
                    sendNotLoginMessage((ServerPlayerEntity) player);
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if (!world.isClient) {
                if (!RainAuth.getInstance().getPlayers().get(player.getUuid())) {
                    sendNotLoginMessage((ServerPlayerEntity) player);
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!world.isClient) {
                if (!RainAuth.getInstance().getPlayers().get(player.getUuid())) {
                    sendNotLoginMessage((ServerPlayerEntity) player);
                    return TypedActionResult.fail(player.getStackInHand(hand));
                }
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });
    }

    private void sendNotLoginMessage(ServerPlayerEntity player) {
        if (notLoginMessageDelay.containsKey(player.getUuid())) {
            if (notLoginMessageDelay.get(player.getUuid()) >= 0 && notLoginMessageDelay.get(player.getUuid()) < 25) {
                notLoginMessageDelay.put(player.getUuid(), notLoginMessageDelay.get(player.getUuid()) + 1);
                return;
            } else {
                notLoginMessageDelay.put(player.getUuid(), 0);
            }
        } else {
            notLoginMessageDelay.put(player.getUuid(), 0);
        }

        if (RainAuth.getInstance().getData().hasRegistered(player.getUuid())) {
            player.sendMessage(new TranslatableText(TranslatableLanguage.get().get("message.rainauth.not_login"))
                    .setStyle(Style.EMPTY.withColor(Formatting.RED)), false);
        } else {
            player.sendMessage(new TranslatableText(TranslatableLanguage.get().get("message.rainauth.not_register"))
                    .setStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
        }
        notLoginMessageDelay.put(player.getUuid(), notLoginMessageDelay.get(player.getUuid()) + 1);

        Networking.openAuthGui(player);
    }
}
