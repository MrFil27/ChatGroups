package io.github.mrfil27.chatgroups.utils;

import io.github.mrfil27.chatgroups.ChatGroups;

public class Configs {

    public static void createConfig(ChatGroups plugin){
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
    }

    public static boolean checkRealisticDistance(ChatGroups plugin){
        int defaultDistance = plugin.getConfig().getInt("chat-radius");
        int screamDistance = plugin.getConfig().getInt("default-prefixes.scream.radius");
        return defaultDistance > screamDistance;
    }

}
