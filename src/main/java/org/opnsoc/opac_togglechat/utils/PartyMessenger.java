package org.opnsoc.opac_togglechat.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xaero.pac.common.server.ServerData;
import xaero.pac.common.server.parties.command.PartyOnCommandUpdater;
import xaero.pac.common.server.parties.party.IServerParty;
import xaero.pac.common.parties.party.member.IPartyMember;
import xaero.pac.common.parties.party.ally.IPartyAlly;
import xaero.pac.common.parties.party.IPartyPlayerInfo;

public class PartyMessenger {
    public static void sendPartyMessage(ServerPlayer sender, String text) {
        var server = sender.getServer();
        var serverData = ServerData.from(server);
        var partyManager = serverData.getPartyManager();
        IServerParty<IPartyMember, IPartyPlayerInfo, IPartyAlly> party = partyManager.getPartyByMember(sender.getUUID());
        if (party == null) {
            sender.sendSystemMessage(Component.literal("§cYou are not in a party!"));
            return;
        }
        Component message = Component.literal("<" + sender.getName().getString() + "> ")
                .append(Component.literal(text).withStyle(s -> s.withColor(ChatFormatting.GRAY)));
        new PartyOnCommandUpdater().update(sender.getUUID(), serverData, party, serverData.getPlayerConfigs(), mi -> false, message);
    }
}