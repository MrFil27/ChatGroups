package io.github.mrfil27.chatgroups.api;

import io.github.mrfil27.chatgroups.api.manager.ActionManager;
import io.github.mrfil27.chatgroups.api.manager.ChatGroupManager;
import org.bukkit.entity.Player;

public interface ChatGroupsAPI {

    boolean hasActiveChat(Player player);
    ActionManager getActionManager();
    ChatGroupManager getGroupManager();

}
