package cx.rain.mc.fabric.rainauth.command;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class Commands {
    public Commands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            new CommandRegister(dispatcher);
        });
    }
}
