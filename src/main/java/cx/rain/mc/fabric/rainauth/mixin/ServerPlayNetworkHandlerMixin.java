package cx.rain.mc.fabric.rainauth.mixin;

import cx.rain.mc.fabric.rainauth.event.callback.PlayerCommandCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerMoveCallback;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "onPlayerMove", cancellable = true)
    public void beforeMovePlayer(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (PlayerMoveCallback.EVENT.invoker()
                .accept(player, player.world,
                        new Vec3d(packet.getX(player.getX()),
                                packet.getY(player.getY()),
                                packet.getZ(player.getZ())))
                == ActionResult.FAIL) {
            ci.cancel();

            ServerSidePacketRegistry.INSTANCE.sendToPlayer(player,
                    new EntityPositionS2CPacket(player));
        }
    }

    @Inject(at = @At("HEAD"), method = "executeCommand", cancellable = true)
    public void beforeExecuteCommand(String input, CallbackInfo ci) {
        TypedActionResult<String> result = PlayerCommandCallback.EVENT.invoker().accept(input, player);
        if (result.getResult() == ActionResult.FAIL) {
            player.sendMessage(new LiteralText(result.getValue()), false);
            ci.cancel();
        }
    }
}
