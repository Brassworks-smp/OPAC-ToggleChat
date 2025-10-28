package org.opnsoc.opac_togglechat;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.opnsoc.opac_togglechat.command.CommandRegister;
import org.opnsoc.opac_togglechat.command.OpmToggleCommand;
import org.opnsoc.opac_togglechat.util.PartyMessenger;
import org.slf4j.Logger;


@Mod(Opac_togglechat.MODID)
public class Opac_togglechat {
    public static final String MODID = "opac_togglechat";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Opac_togglechat() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        boolean active = OpmToggleCommand.PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false);

        if (active) {
            PartyMessenger.sendPartyMessage(player, event.getMessage().getString());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        new CommandRegister().register(event.getDispatcher(), event.getCommandSelection());
        LOGGER.info("Registered server commands");
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        LOGGER.info("OPAC-ToggleChat successfully loaded");
    }
}
