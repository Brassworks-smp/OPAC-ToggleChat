package org.opnsoc.opac_togglechat.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import xaero.pac.common.server.parties.command.CommandRequirementProvider;

public class CommandRegister {
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
        CommandRequirementProvider commandRequirementProvider = new CommandRequirementProvider();
        new OpmStatusCommand().register(dispatcher, environment, commandRequirementProvider);
        new OpmToggleCommand().register(dispatcher, environment, commandRequirementProvider);
    }
}
