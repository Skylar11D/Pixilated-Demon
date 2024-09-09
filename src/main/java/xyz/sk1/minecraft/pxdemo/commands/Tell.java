package xyz.sk1.minecraft.pxdemo.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Tell extends Command {

    public Tell(String name, String... aliases) {
        super("pl", "plugins", "core", "server");

        setDefaultExecutor(((commandSender, commandContext) -> {

        }));

    }

}
