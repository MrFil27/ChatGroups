package io.github.mrfil27.chatgroups.api;

import io.github.mrfil27.chatgroups.api.execute.ChatControlGroupManager;
import io.github.mrfil27.chatgroups.api.execute.TalkControlActionManager;
import io.github.mrfil27.chatgroups.api.manager.ActionManager;
import io.github.mrfil27.chatgroups.api.manager.ChatGroupManager;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Control implements ChatGroupsAPI{

    private final ActionManager actionManager = new TalkControlActionManager();
    private final ChatGroupManager groupManager = new ChatControlGroupManager();

    @Override
    public boolean hasActiveChat(Player player) {
        UUID playerId = player.getUniqueId();
        return GroupControl.playerChats.containsKey(playerId);
    }

    @Override
    public ActionManager getActionManager() {
        return actionManager;
    }

    @Override
    public ChatGroupManager getGroupManager() {
        return groupManager;
    }
}
