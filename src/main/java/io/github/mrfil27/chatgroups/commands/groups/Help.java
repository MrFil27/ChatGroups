package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.GroupManager;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Help extends SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Lista dei comandi di ChatGroups";
    }

    @Override
    public String getSyntax() {
        return "/cg help";
    }

    @Override
    public void perform(Player p, String[] args) throws IOException {
        if(!p.hasPermission("cg.use")){
            p.sendMessage(ChatGroups.format("&cNon hai il permesso di eseguire questo comando."));
            return;
        }

        if(args.length != 1){
            p.sendMessage(ChatGroups.format("&cSintassi errata, usa &7" + getSyntax()));
            return;
        }

        p.sendMessage(ChatGroups.format("&a&lComandi di ChatGroups"));

        if(p.hasPermission("cg.admin")){
            for(SubCommand subCommand : GroupManager.getSubCommands()){
                p.sendMessage(ChatGroups.format("&a" + subCommand.getSyntax() + " &8» &7" + subCommand.getDescription()));
            }
        }else if(p.hasPermission("cg.owner")){
            p.sendMessage(ChatGroups.format("&a/cg help &8» &7Lista dei comandi di ChatGroups"));
            p.sendMessage(ChatGroups.format("&a/cg addowner <chat> <player> &8» &7Aggiungi un operatore a una chat"));
            p.sendMessage(ChatGroups.format("&a/cg removeowner <chat> <player> &8» &7Rimuovi un operatore da una chat"));
            p.sendMessage(ChatGroups.format("&a/cg addmember <chat> <player> &8» &7Aggiungi un membro a una chat"));
            p.sendMessage(ChatGroups.format("&a/cg removemember <chat> <player> &8» &7Rimuovi un membro da una chat"));
        }
    }
}
