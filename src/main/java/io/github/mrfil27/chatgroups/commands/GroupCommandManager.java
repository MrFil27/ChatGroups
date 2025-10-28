package io.github.mrfil27.chatgroups.commands;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.groups.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

public class GroupCommandManager implements CommandExecutor {

    private static final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public GroupCommandManager(ChatGroups plugin){
        subCommands.add(new Help());
        subCommands.add(new Create(plugin));
        subCommands.add(new AddOwner(plugin));
        subCommands.add(new RemoveOwner(plugin));
        subCommands.add(new AddMember(plugin));
        subCommands.add(new RemoveMember(plugin));
        subCommands.add(new Toggle(plugin));
        subCommands.add(new Reload(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player p)){
            sender.sendMessage(ChatGroups.format("&cNon puoi eseguire il comando da questa istanza."));
            return true;
        }

        if(args.length == 0){
            p.sendMessage(ChatGroups.format("&cInserisci gli argomenti necessari."));
            return true;
        }

        for(int i = 0; i < getSubCommands().size(); i++){
            if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                try {
                    getSubCommands().get(i).perform(p, args);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }

        return true;
    }

    public static ArrayList<SubCommand> getSubCommands(){
        return subCommands;
    }

}
