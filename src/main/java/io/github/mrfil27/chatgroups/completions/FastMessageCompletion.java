package io.github.mrfil27.chatgroups.completions;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FastMessageCompletion implements TabCompleter {

    private final ChatGroups plugin;

    public FastMessageCompletion(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player player)){
            return Collections.emptyList();
        }

        if(!player.hasPermission("cg.use")){
            return Collections.emptyList();
        }

        GroupControl control = new GroupControl(plugin);

        if(args.length == 1){
            List<String> chats = control.getGroupsOfPlayer(player);
            chats.add("global");

            return chats.stream()
                    .filter(chat -> chat.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return List.of();
    }
}
