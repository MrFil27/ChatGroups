package io.github.mrfil27.chatgroups.api.execute;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.api.manager.ChatGroupManager;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.entity.Player;

public class ChatControlGroupManager implements ChatGroupManager {

    private final ChatGroups plugin;

    public ChatControlGroupManager(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public void create(String name, Player owner) {
        new GroupControl(plugin).createChatGroup(owner, name);
    }

    @Override
    public void addMember(String name, Player player) {
        new GroupControl(plugin).addMember(player, name);
    }

    @Override
    public void removeMember(String name, Player player) {
        new GroupControl(plugin).removeMember(player, name);
    }

    @Override
    public void addOwner(String name, Player player) {
        new GroupControl(plugin).addOwner(player, name);
    }

    @Override
    public void removeOwner(String name, Player player) {
        new GroupControl(plugin).removeOwner(player, name);
    }

    @Override
    public void toggle(String name, Player player) {
        new GroupControl(plugin).toggleChat(player, name);
    }
}
