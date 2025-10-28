package io.github.mrfil27.chatgroups.events;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerTalk implements Listener {

    private final ChatGroups plugin;

    public PlayerTalk(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerTalk(AsyncPlayerChatEvent e){
        Player sender = e.getPlayer();
        int chatRadius = plugin.getConfig().getInt("chat-radius");

        Set<Player> nearbyPlayers = sender.getWorld().getPlayers().stream()
                .filter(player -> player.getLocation().distance(sender.getLocation()) <= chatRadius)
                .collect(Collectors.toSet());

        sender.getServer().getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("cg.admin"))
                .forEach(nearbyPlayers::add);

        e.getRecipients().clear();
        e.getRecipients().addAll(nearbyPlayers);
    }

    @EventHandler
    public void playerTalkOnChat(AsyncPlayerChatEvent e){
        Player sender = e.getPlayer();
        String originalMessage = e.getMessage();

        GroupControl control = new GroupControl(plugin);

        String chat = GroupControl.getActiveChat(sender);

        if(control.hasActiveChat(sender)){
            e.setCancelled(true);
            String prefix = plugin.getConfig().getString("groups." + chat + ".prefix");
            String formattedMessage;
            if(prefix != null && prefix.equals("&8(&a&lSTAFF&8)")){
                formattedMessage = ChatGroups.format(prefix + " &f" + sender.getName() + " &8» &b" + originalMessage);
            }else formattedMessage = ChatGroups.format(prefix + " &f" + sender.getName() + " &8» &7" + originalMessage);

            List<String> members = control.getChatMembers(chat);
            for(String memberUuid : members){
                Player member = Bukkit.getPlayer(UUID.fromString(memberUuid));
                if(member != null && member.isOnline()){
                    member.sendMessage(formattedMessage);
                }
            }
        }
    }

}
