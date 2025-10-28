package io.github.mrfil27.chatgroups;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CommandManager {

    private final ChatGroups plugin;

    public CommandManager(ChatGroups plugin) {
        this.plugin = plugin;
    }

    public void register(String commandName, CommandExecutor executor){
        PluginCommand cmd = plugin.getCommand(commandName);

        if(cmd == null){
            plugin.getLogger().severe("Command '" + commandName +
                    "' not defined in plugin.yml!");
            return;
        }

        cmd.setExecutor(wrapExecutor(executor));
        plugin.getLogger().info("Comando '/" + commandName + "' registrato correttamente.");
    }

    private CommandExecutor wrapExecutor(CommandExecutor original){
        return(sender, command, label, args) -> {
            try{
                return original.onCommand(sender, command, label, args);
            }catch(Exception e){
                String msg = "§cErrore interno, contatta un amministratore.";
                sender.sendMessage(msg);
                plugin.getLogger().log(Level.SEVERE,
                        "Error executing command '/" + label + "' by "
                                + (sender instanceof Player ? sender.getName() : "Console"),
                        e);
                return true;
            }
        };
    }
}
