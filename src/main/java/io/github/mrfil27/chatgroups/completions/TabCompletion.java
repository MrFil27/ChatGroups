package io.github.mrfil27.chatgroups.completions;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TabCompletion implements TabCompleter {

    private final ChatGroups plugin;

    public TabCompletion(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            return Collections.emptyList();
        }

        if(!player.hasPermission("cg.use")){
            return Collections.emptyList();
        }

        if(args.length == 1){
            if(player.hasPermission("cg.admin")){
                return Stream.of("help", "create", "addmember", "removemember", "addowner", "removeowner", "toggle", "reload")
                        .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            }else if(player.hasPermission("cs.use")){
                return Stream.of("help", "addmember", "removemember", "toggle")
                        .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            }
            return Stream.of("toggle")
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        GroupControl control = new GroupControl(plugin);

        if(args.length == 2){
            List<String> completions = new ArrayList<>();
            if(args[0].equalsIgnoreCase("create")){
                completions.add("<name>");
                return completions;
            }
            if(args[0].equalsIgnoreCase("addmember") || args[0].equalsIgnoreCase("removemember") ||
                args[0].equalsIgnoreCase("addowner") || args[0].equalsIgnoreCase("removeowner")){
                return control.getGroupsOwnedByPlayer(player);
            }
            if(args[0].equalsIgnoreCase("toggle")){
                List<String> chats = control.getGroupsOfPlayer(player);
                chats.add("global");

                return chats.stream()
                        .filter(chat -> chat.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        if(args.length == 3){
            List<String> completions = new ArrayList<>();
            if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("addmember")){
                Bukkit.getOnlinePlayers().forEach(player1 -> completions.add(player1.getName()));
                return completions;
            }
            if(args[0].equalsIgnoreCase("removeowner")){
                String chat = args[1];
                List<String> owners = control.getChatOwners(chat);

                return owners.stream()
                        .map(ownerUuid -> {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(ownerUuid));
                            return offlinePlayer.getName();
                        })
                        .filter(name -> name != null && name.toLowerCase().startsWith(args[2].toLowerCase()))
                        .collect(Collectors.toList());
            }
            if(args[0].equalsIgnoreCase("removemember")){
                String chat = args[1];
                List<String> members = control.getChatMembers(chat);

                return members.stream()
                        .map(memberUuid -> {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(memberUuid));
                            return offlinePlayer.getName();
                        })
                        .filter(name -> name != null && name.toLowerCase().startsWith(args[2].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        return null;
    }
}
