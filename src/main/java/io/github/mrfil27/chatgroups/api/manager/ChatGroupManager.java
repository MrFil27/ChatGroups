package io.github.mrfil27.chatgroups.api.manager;

import org.bukkit.entity.Player;

public interface ChatGroupManager {

    void create(String name, Player owner);
    void addMember(String name, Player player);
    void removeMember(String name, Player player);
    void addOwner(String name, Player player);
    void removeOwner(String name, Player player);
    void toggle(String name, Player player);

}
