package cx.rain.mc.fabric.rainauth.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import cx.rain.mc.fabric.rainauth.utility.TranslatableLanguage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.*;

public class CommandRegister {
    public CommandRegister(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> reg =
                dispatcher.register(literal("reg")
                        .then(PasswordArgumentBuilder.<ServerCommandSource, String>create("password", StringArgumentType.string())
                                .then(PasswordArgumentBuilder.<ServerCommandSource, String>create("repeat_password", StringArgumentType.string())
                                        .executes(this::register))));

        LiteralCommandNode<ServerCommandSource> register =
                dispatcher.register(literal("register")
                        .then(PasswordArgumentBuilder.<ServerCommandSource, String>create("password", StringArgumentType.string())
                                .then(PasswordArgumentBuilder.<ServerCommandSource, String>create("repeat_password", StringArgumentType.string())
                                        .executes(this::register))));

        LiteralCommandNode<ServerCommandSource> forceRegister =
                dispatcher.register(literal("register")
                        .requires((ctx) -> ctx.hasPermissionLevel(2))
                            .then(argument("name", StringArgumentType.string())
                                    .then(PasswordArgumentBuilder.<ServerCommandSource, String>create("password", StringArgumentType.string())
                                            .then(PasswordArgumentBuilder.<ServerCommandSource, String>create("repeat_password", StringArgumentType.string())
                                                    .executes(this::registerForce)))));
    }

    public int register(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player;
        try {
            player = context.getSource().getPlayer();
        } catch (CommandSyntaxException ex) {
            context.getSource().sendError(new LiteralText(TranslatableLanguage.get()
                    .get("message.rainauth.not_player")));
            return 1;
        }

        if (!context.getArgument("password", String.class)
                .equals(context.getArgument("repeat_password", String.class))) {
            context.getSource().sendError(new LiteralText(TranslatableLanguage.get()
                    .get("message.rainauth.not_repeat")));
            return 1;
        }


        return 1;
    }

    private int registerForce(CommandContext<ServerCommandSource> context) {

        return 1;
    }

}
