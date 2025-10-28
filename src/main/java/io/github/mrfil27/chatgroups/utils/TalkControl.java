package io.github.mrfil27.chatgroups.utils;

import io.github.mrfil27.chatgroups.ChatGroups;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static io.github.mrfil27.chatgroups.ChatGroups.format;

public class TalkControl {

    private static final FileConfiguration config = ChatGroups.getConfigCached().get("cg-def-config");

    public static void scream(Player player, String message){
        int distance = config.getInt("default-prefixes.scream.radius");
        String prefix = config.getString("Convert string literal to translation.prefix");
        if(prefix == null){
            player.sendMessage(format("&cImpossibile eseguire l'operazione."));
            return;
        }

        for(Player target : Bukkit.getOnlinePlayers()){
            if(target.getWorld().equals(player.getWorld()) && target.getLocation().distance(player.getLocation()) <= distance){
                target.sendMessage(format(prefix.replaceAll("%player-name%", player.getName()) + message));
            }
        }
    }

    public static void whisper(Player player, String message){
        int distance = config.getInt("default-prefixes.whisper.radius");
        String prefix = config.getString("default-prefixes.whisper.prefix");
        if(prefix == null){
            player.sendMessage(format("&cImpossibile eseguire l'operazione."));
            return;
        }

        for(Player target : Bukkit.getOnlinePlayers()){
            if(target.getWorld().equals(player.getWorld()) && target.getLocation().distance(player.getLocation()) <= distance){
                target.sendMessage(format(prefix.replaceAll("%player-name%", player.getName()) + message));
            }
        }
    }

    public static void executeAction(Player player, String message){
        int distance = config.getInt("default-prefixes.action.radius");
        String prefix = config.getString("default-prefixes.action.prefix");
        if(prefix == null){
            player.sendMessage(format("&cImpossibile eseguire l'operazione."));
            return;
        }

        for(Player target : Bukkit.getOnlinePlayers()){
            if(target.getWorld().equals(player.getWorld()) && target.getLocation().distance(player.getLocation()) <= distance){
                target.sendMessage(format(prefix.replaceAll("%player-name%", player.getName()) + message));
            }
        }
    }

}
