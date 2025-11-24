package io.github.mrfil27.chatgroups.api;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.api.execute.ChatControlGroupManager;
import io.github.mrfil27.chatgroups.api.execute.ActionManagerImpl;
import io.github.mrfil27.chatgroups.api.manager.ActionManager;
import io.github.mrfil27.chatgroups.api.manager.ChatGroupManager;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatGroupsImpl implements ChatGroupsAPI{

    private final ActionManager actionManager;
    private final ChatGroupManager groupManager;

    public ChatGroupsImpl(ChatGroups plugin) {
        this.actionManager = new ActionManagerImpl();
        this.groupManager = new ChatControlGroupManager(plugin);
    }

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
