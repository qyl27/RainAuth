package cx.rain.mc.fabric.rainauth.mixin;

import cx.rain.mc.fabric.rainauth.event.callback.PlayerLeaveCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLoginCallback;
import cx.rain.mc.fabric.rainauth.interfaces.IPlayerList;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin implements IPlayerList {
    @Final
    @Shadow
    private List<ServerPlayerEntity> players;

    @Inject(at = @At("HEAD"), method = "onPlayerConnect", cancellable = true)
    public void beforeLogin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        if (PlayerLoginCallback.EVENT.invoker().accept(connection, player) == ActionResult.FAIL) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "remove", cancellable = true)
    public void beforeRemove(ServerPlayerEntity player, CallbackInfo ci) {
        PlayerLeaveCallback.EVENT.invoker().accept(player);
    }

    @Override
    public List<ServerPlayerEntity> getPlayers() {
        return players;
    }
}
