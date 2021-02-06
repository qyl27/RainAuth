package cx.rain.mc.fabric.rainauth.event;

import cx.rain.mc.fabric.rainauth.RainAuth;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLeaveCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLoginCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerMoveCallback;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;

import java.util.UUID;

public class Events {
    public Events() {
        PlayerLoginCallback.EVENT.register(((connection, player) -> {
            RainAuth.getInstance().getPlayerJoined().add(player.getUuid());
            return ActionResult.PASS;
        }));

        PlayerLeaveCallback.EVENT.register(((player) -> {
            UUID uuid = player.getUuid();
            if (RainAuth.getInstance().getPlayerJoined().contains(uuid)) {
                RainAuth.getInstance().getPlayerJoined().remove(uuid);
            }
        }));

        PlayerMoveCallback.EVENT.register(((player, world, direction) -> {
            if (!RainAuth.getInstance().getPlayerJoined().contains(player.getUuid())) {
                player.setPos(player.prevX, player.prevY, player.prevZ);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }));

        AttackBlockCallback.EVENT.register((playerEntity, world, hand, blockPos, direction) -> {
            if (!RainAuth.getInstance().getPlayerJoined().contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (!RainAuth.getInstance().getPlayerJoined().contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult) -> {
            if (!RainAuth.getInstance().getPlayerJoined().contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (!RainAuth.getInstance().getPlayerJoined().contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            if (!RainAuth.getInstance().getPlayerJoined().contains(playerEntity.getUuid())) {
                sendNotLoginMessage(playerEntity);
                return TypedActionResult.fail(playerEntity.getStackInHand(hand));
            }
            return TypedActionResult.pass(playerEntity.getStackInHand(hand));
        });
    }

    private void sendNotLoginMessage(PlayerEntity player) {
        if (RainAuth.getInstance().getData().hasRegistered(player.getName().asString(), player.getUuid())) {
            player.sendMessage(new TranslatableText("message.rainauth.not_login")
                    .setStyle(Style.EMPTY.withColor(Formatting.RED)), false);
        } else {
            player.sendMessage(new TranslatableText("message.rainauth.not_register")
                    .setStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
        }
    }
}
