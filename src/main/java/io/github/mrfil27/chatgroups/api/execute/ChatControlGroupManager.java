package io.github.mrfil27.chatgroups.api.execute;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.api.manager.ChatGroupManager;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

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
    public void removeMember(String name, OfflinePlayer player) {
        new GroupControl(plugin).removeMember(player, name);
    }

    @Override
    public void addOwner(String name, Player player) {
        new GroupControl(plugin).addOwner(player, name);
    }

    @Override
    public void removeOwner(String name, OfflinePlayer player) {
        new GroupControl(plugin).removeOwner(player, name);
    }

    @Override
    public void toggle(String name, Player player) {
        new GroupControl(plugin).toggleChat(player, name);
    }

    @Override
    public void delete(String name) {
        new GroupControl(plugin).deleteChat(name);
    }

    @Override
    public void sendMessage(String chat, String message) {
        String prefix = plugin.getConfig().getString("groups." + chat + ".prefix");
        String formattedMessage = ChatGroups.format(prefix + " &f%player% &8» &7" + message);
        List<String> members = new GroupControl(plugin).getChatMembers(chat);;
        for(String memberUuid : members){
            Player member = Bukkit.getPlayer(UUID.fromString(memberUuid));
            if(member != null && member.isOnline()) member.sendMessage(formattedMessage.replace("%player%", member.getName()));
        }
    }
}
