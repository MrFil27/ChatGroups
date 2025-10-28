package io.github.mrfil27.chatgroups.commands;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static io.github.mrfil27.chatgroups.ChatGroups.format;

public class FastMessage implements CommandExecutor {

    private final ChatGroups plugin;

    public FastMessage(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player p)){
            sender.sendMessage(format("&cYou can't execute this command from this instance."));
            return true;
        }

        if(args.length < 1){
            p.sendMessage(format("Sintassi errata, usa &7/msg <chat> <messaggio>"));
            return true;
        }

        GroupControl control = new GroupControl(plugin);
        String chat = args[0];

        if(!control.groupExists(chat)){
            p.sendMessage(ChatGroups.format("&cIl gruppo indicato risulta inesistente."));
            return true;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        sendGroupMessage(control, chat, message, p);

        return true;
    }

    private void sendGroupMessage(GroupControl control, String chat, String originalMessage, Player sender){
        String prefix = plugin.getConfig().getString("groups." + chat + ".prefix");
        String formattedMessage = ChatGroups.format(prefix + " &f" + sender.getName() + " &8» &7" + originalMessage);
        List<String> members = control.getChatMembers(chat);
        for(String memberUuid : members){
            Player member = Bukkit.getPlayer(UUID.fromString(memberUuid));
            if(member != null && member.isOnline()){
                member.sendMessage(formattedMessage);
            }
        }
    }
}
