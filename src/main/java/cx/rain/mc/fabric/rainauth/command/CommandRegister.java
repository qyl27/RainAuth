package cx.rain.mc.fabric.rainauth.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import cx.rain.mc.fabric.rainauth.RainAuth;
import cx.rain.mc.fabric.rainauth.data.ConfigManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class CommandRegister {
    public CommandRegister(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> register = dispatcher.register(CommandManager.literal("register")
                .then(CommandManager.argument("password", StringArgumentType.string())
                        .then(CommandManager.argument("repeatPassword", StringArgumentType.string())
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof PlayerEntity) {
                                        PlayerEntity player = (PlayerEntity) context.getSource().getEntity();
                                        ConfigManager data = RainAuth.INSTANCE.getData();
                                        String password = context.getArgument("password", String.class);
                                        String passwordRepeat =
                                                context.getArgument("repeatPassword", String.class);
                                        if (password.equals(passwordRepeat)) {
                                            if (!data.hasRegistered(player.getName().asString(), player.getUuid())) {
                                                data.register(player.getName().asString(), player.getUuid(), password);
                                            } else {
                                                context.getSource().sendError(
                                                        new TranslatableText(
                                                                "message.rainauth.already_registered"));
                                            }
                                        } else {
                                            context.getSource().sendError(
                                                    new TranslatableText("message.rainauth.not_repeat"));
                                        }
                                    } else {
                                        context.getSource().sendError(
                                                new TranslatableText("message.rainauth.not_player"));
                                    }
                                    return 1;
                                }))));

        dispatcher.register(CommandManager.literal("reg").redirect(register));
    }
}
