package cx.rain.mc.fabric.rainauth.command;

import cx.rain.mc.fabric.rainauth.RainAuth;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;

public class Commands {
    public Commands() {
        ArgumentTypes.register(RainAuth.MODID + ":password", PasswordArgumentType.class,
                new ConstantArgumentSerializer<>(PasswordArgumentType::password));

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> new CommandRegister(dispatcher));
    }
}
