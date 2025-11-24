package io.github.mrfil27.chatgroups.utils;

import io.github.mrfil27.chatgroups.ChatGroups;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class GroupControl {

    private final ChatGroups plugin;
    public static final Map<UUID, String> playerChats = new HashMap<>();
    private final FileConfiguration config = ChatGroups.getConfigCached().get("cg-def-config");

    public GroupControl(ChatGroups plugin) {
        this.plugin = plugin;
    }

    // === Booleans ===

    public boolean groupExists(String name){
        return config.contains("groups." + name) || name.equals("global");
    }

    public boolean isPlayerDistant(Player player, Player target){
        return player.getLocation().distance(target.getLocation()) > 5;
    }

    public boolean isOwner(Player player, String groupName){
        return config.getStringList("groups." + groupName + ".owners").contains(player.getUniqueId().toString());
    }

    public boolean isMember(Player player, String groupName){
        return config.getStringList("groups." + groupName + ".members").contains(player.getUniqueId().toString());
    }

    public boolean isMember(OfflinePlayer player, String groupName){
        return config.getStringList("groups." + groupName + ".members").contains(player.getUniqueId().toString());
    }

    public boolean isOwner(OfflinePlayer player, String groupName){
        return config.getStringList("groups." + groupName + ".owners").contains(player.getUniqueId().toString());
    }

    public boolean hasMembers(String name){
        List<String> members = config.getStringList("groups." + name + ".members");
        return members.isEmpty();
    }

    public boolean hasAnotherChatToggled(Player player, String chat) {
        UUID playerId = player.getUniqueId();
        String currentChat = playerChats.get(playerId);

        if(currentChat != null){
            return !currentChat.equals(chat) || chat.equals("global");
        }

        return false;
    }

    public boolean hasActiveChat(Player player){
        UUID playerId = player.getUniqueId();
        return playerChats.containsKey(playerId);
    }

    // === Gets ===

    public List<String> getGlobalChatMembers(){
        if(config.contains("groups.global")){
            return config.getStringList("groups.global.members");
        }
        return new ArrayList<>();
    }

    public List<String> getGroupsOwnedByPlayer(Player player){
        List<String> groupsOwned = new ArrayList<>();
        String playerUuid = player.getUniqueId().toString();

        for(String groupName : Objects.requireNonNull(plugin.getConfig().getConfigurationSection("groups")).getKeys(false)){
            List<String> owners = config.getStringList("groups." + groupName + ".owners");

            if(owners.contains(playerUuid)){
                groupsOwned.add(groupName);
            }
        }
        return groupsOwned;
    }

    public List<String> getGroupsOfPlayer(Player player){
        List<String> groups = new ArrayList<>();
        String playerUuid = player.getUniqueId().toString();

        for(String groupName : Objects.requireNonNull(config.getConfigurationSection("groups")).getKeys(false)){
            List<String> members = config.getStringList("groups." + groupName + ".members");

            if(members.contains(playerUuid)){
                groups.add(groupName);
            }
        }
        for(String groupName : Objects.requireNonNull(config.getConfigurationSection("groups")).getKeys(false)){
            List<String> members = config.getStringList("groups." + groupName + ".owners");

            if(members.contains(playerUuid)){
                groups.add(groupName);
            }
        }
        return groups;
    }

    public static String getActiveChat(Player player){
        return playerChats.getOrDefault(player.getUniqueId(), "global");
    }

    public List<String> getChatMembers(String chat){
        if(config.contains("groups." + chat)) {
            return config.getStringList("groups." + chat + ".members");
        }
        return new ArrayList<>();
    }

    public List<String> getChatOwners(String chat){
        if(config.contains("groups." + chat)){
            return config.getStringList("groups." + chat + ".owners");
        }
        return new ArrayList<>();
    }

    // === Voids ===

    public void createChatGroup(Player owner, String name){
        List<String> owners = config.getStringList("groups." + name + ".owners");
        if(owners.isEmpty()) owners = new ArrayList<>();

        owners.add(owner.getUniqueId().toString());
        plugin.getConfig().set("groups." + name + ".owners", owners);
        plugin.getConfig().set("groups." + name + ".members", owners);
        plugin.getConfig().set("groups." + name + ".prefix", "\"" + name + "\"");
        plugin.saveConfig();
        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());
    }

    public void addOwner(Player target, String name){
        String uuid = target.getUniqueId().toString();
        List<String> owners = plugin.getConfig().getStringList("groups." + name + ".owners");
        List<String> members = plugin.getConfig().getStringList("groups." + name + ".members");
        if(owners.isEmpty()){
            owners = new ArrayList<>();
        }
        owners.add(uuid);
        plugin.getConfig().set("groups." + name + ".owners", owners);
        if(!members.contains(uuid)){
            members.add(uuid);
            plugin.getConfig().set("groups." + name + ".members", members);
        }
        plugin.saveConfig();
        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());
    }

    public void removeOwner(OfflinePlayer target,  String name){
        List<String> owners = plugin.getConfig().getStringList("groups." + name + ".owners");
        if(owners.isEmpty()){
            owners = new ArrayList<>();
        }
        owners.remove(target.getUniqueId().toString());
        plugin.getConfig().set("groups." + name + ".owners", owners);
        plugin.saveConfig();
        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());
    }

    public void addMember(Player target,  String name){
        List<String> members = plugin.getConfig().getStringList("groups." + name + ".members");
        if(members.isEmpty()){
            members = new ArrayList<>();
        }
        members.add(target.getUniqueId().toString());
        plugin.getConfig().set("groups." + name + ".members", members);
        plugin.saveConfig();
        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());
    }

    public void removeMember(Player target,  String name){
        List<String> members = plugin.getConfig().getStringList("groups." + name + ".members");
        if(members.isEmpty()){
            members = new ArrayList<>();
        }
        members.remove(target.getUniqueId().toString());
        plugin.getConfig().set("groups." + name + ".members", members);
        plugin.saveConfig();
        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());
    }

    public void removeMember(OfflinePlayer target,  String name){
        List<String> members = plugin.getConfig().getStringList("groups." + name + ".members");
        if(members.isEmpty()){
            members = new ArrayList<>();
        }
        members.remove(target.getUniqueId().toString());
        plugin.getConfig().set("groups." + name + ".members", members);
        plugin.saveConfig();
        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());
    }

    public void toggleChat(Player player, String chat) {
        UUID playerId = player.getUniqueId();

        if(chat.equalsIgnoreCase("global")){
            if(playerChats.containsKey(playerId)){
                playerChats.remove(playerId);
                player.sendMessage(ChatGroups.format("&aOra stai parlando nella chat globale."));
            }else{
                player.sendMessage(ChatGroups.format("&aSei già nella chat globale."));
            }
            return;
        }

        String currentChat = playerChats.get(playerId);
        if(chat.equals(currentChat)){
            playerChats.remove(playerId);
            player.sendMessage(ChatGroups.format("&aOra stai parlando nella chat globale."));
        }else{
            playerChats.put(playerId, chat);
            player.sendMessage(ChatGroups.format("&aOra stai parlando nella chat &f" + chat + "&a."));
        }
    }

    public void deleteChat(String name){
        plugin.getConfig().set("groups." + name, null);
        plugin.saveConfig();
        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());
    }

}
