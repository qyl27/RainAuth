package cx.rain.mc.fabric.rainauth.interfaces;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public interface IPlayerList {
    public List<ServerPlayerEntity> getPlayers();
}
