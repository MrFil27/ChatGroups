package io.github.mrfil27.chatgroups.commands.talk;

import io.github.mrfil27.chatgroups.utils.TalkControl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.mrfil27.chatgroups.ChatGroups.format;

public class Action implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)){
            sender.sendMessage(format("&cYou can't execute that command frm this instance."));
            return true;
        }

        if(args.length == 0){
            p.sendMessage(format("&cSintassi errata, usa &7/urlo <messaggio>"));
            return true;
        }

        String message = String.join(" ", args);

        TalkControl.executeAction(p, message);

        return true;
    }
}
