package io.github.mrfil27.chatgroups;

import io.github.mrfil27.chatgroups.api.ChatGroupsAPI;
import io.github.mrfil27.chatgroups.api.Control;
import io.github.mrfil27.chatgroups.commands.FastMessage;
import io.github.mrfil27.chatgroups.commands.GroupCommandManager;
import io.github.mrfil27.chatgroups.commands.talk.Action;
import io.github.mrfil27.chatgroups.commands.talk.Scream;
import io.github.mrfil27.chatgroups.commands.talk.Whisper;
import io.github.mrfil27.chatgroups.completions.FastMessageCompletion;
import io.github.mrfil27.chatgroups.completions.TabCompletion;
import io.github.mrfil27.chatgroups.events.JoinGlobal;
import io.github.mrfil27.chatgroups.events.PlayerTalk;
import io.github.mrfil27.chatgroups.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ChatGroups extends JavaPlugin {

    private static ChatGroupsAPI chatAPI;
    private static final Map<String, FileConfiguration> configCached = new HashMap<>();

    @Override
    public void onEnable() {
        //APIs
        chatAPI = new Control();

        //Configs
        Configs.createConfig(this);
        if(Configs.checkRealisticDistance(this)){
            Bukkit.getConsoleSender().sendMessage(format("&c[ChatGroups] Scream distance must be greater than default distance."));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        configCached.put("cg-def-config", getConfig());

        //Commands
        CommandManager cmdMgr = new CommandManager(this);
        cmdMgr.register("chatGroups", new GroupCommandManager(this));
        cmdMgr.register("message", new FastMessage(this));
        cmdMgr.register("action", new Action());
        cmdMgr.register("scream", new Scream());
        cmdMgr.register("whisper", new Whisper());

        //Events
        getServer().getPluginManager().registerEvents(new PlayerTalk(this), this);
        getServer().getPluginManager().registerEvents(new JoinGlobal(this), this);

        //Completions
        Objects.requireNonNull(getCommand("chatGroups")).setTabCompleter(new TabCompletion(this));
        Objects.requireNonNull(getCommand("message")).setTabCompleter(new FastMessageCompletion(this));

        Bukkit.getConsoleSender().sendMessage(format("&2[ChatGroups] &aPlugin has been enabled successfully."));
    }

    public static ChatGroupsAPI getApi(){
        return chatAPI;
    }

    public static Map<String, FileConfiguration> getConfigCached(){
        return configCached;
    }

    public static ChatGroups getInstance(){
        return ChatGroups.getPlugin(ChatGroups.class);
    }

    public static String format(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
