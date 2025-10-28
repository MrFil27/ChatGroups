package io.github.mrfil27.chatgroups.events;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinGlobal implements Listener {

    private final ChatGroups plugin;

    public JoinGlobal(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        String playerUuid = player.getUniqueId().toString();
        GroupControl control = new GroupControl(plugin);
        List<String> globalMembers = control.getGlobalChatMembers();
        if(!globalMembers.contains(playerUuid)){
            globalMembers.add(playerUuid);
            plugin.getConfig().set("groups.global.members", globalMembers);
            plugin.saveConfig();
            Bukkit.getConsoleSender().sendMessage(ChatGroups.format("&2[ChatGroups:info] &a" + player.getName() + " has been added as global chat member."));
        }
    }

}
