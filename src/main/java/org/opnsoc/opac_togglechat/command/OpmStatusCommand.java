package org.opnsoc.opac_togglechat.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xaero.pac.common.server.parties.command.CommandRequirementProvider;

import java.util.function.Predicate;

public class OpmStatusCommand {
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment, CommandRequirementProvider commandRequirementProvider) {
        Predicate<CommandSourceStack> requirement = commandRequirementProvider.getMemberRequirement((party, mi) -> true);
        Command<CommandSourceStack> action = ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            boolean state = OpmToggleCommand.PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false);
            player.sendSystemMessage(Component.literal("Partychat is currently " + (state ? "§aactivated" : "§cdeactivated")));
            return 1;
        };
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("opmstatus")
                .requires(requirement)
                .executes(action);
        dispatcher.register(command);
    }
}