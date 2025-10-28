package org.opnsoc.opac_togglechat.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xaero.pac.common.server.parties.command.CommandRequirementProvider;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

public class OpmToggleCommand {
    public static final HashMap<UUID, Boolean> PARTY_CHAT_ENABLED = new HashMap<>();

    public void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment, CommandRequirementProvider commandRequirementProvider) {
        Predicate<CommandSourceStack> requirement = commandRequirementProvider.getMemberRequirement((party, mi) -> true);

        Command<CommandSourceStack> action = ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            UUID id = player.getUUID();
            boolean newState = !PARTY_CHAT_ENABLED.getOrDefault(id, false);
            PARTY_CHAT_ENABLED.put(id, newState);
            player.sendSystemMessage(Component.literal("Partychat is now " + (newState ? "§aactivated" : "§cdeactivated")));
            return 1;
        };

        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("opmtoggle")
                .requires(requirement)
                .executes(action);
        dispatcher.register(command);
    }
}