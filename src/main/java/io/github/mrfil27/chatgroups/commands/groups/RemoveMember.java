package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.IOException;

public class RemoveMember extends SubCommand {

    private final ChatGroups plugin;

    public RemoveMember(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "removemember";
    }

    @Override
    public String getDescription() {
        return "Rimuovi un membro dal gruppo chat";
    }

    @Override
    public String getSyntax() {
        return "/cg removemember <group> <player>";
    }

    @Override
    public void perform(Player p, String[] args) throws IOException {
        if(!p.hasPermission("cg.use") || !p.hasPermission("cg.admin")){
            p.sendMessage(ChatGroups.format("&cNon hai il permesso di eseguire questo comando."));
            return;
        }

        if(args.length != 3 && args[0].equalsIgnoreCase(getName())){
            p.sendMessage(ChatGroups.format("&cSintassi errata, usa &7/cg help"));
            return;
        }

        String name = args[1];
        GroupControl control = new GroupControl(plugin);

        if(!control.groupExists(name)){
            p.sendMessage(ChatGroups.format("&cIl gruppo indicato risulta inesistente."));
            return;
        }

        String playerName = args[2];
        OfflinePlayer member = Bukkit.getOfflinePlayer(playerName);

        if(!control.isOwner(p, name)){
            p.sendMessage(ChatGroups.format("&cNon risulti owner di questo gruppo."));
            return;
        }

        if(!control.isMember(member, name)){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato non fa parte del gruppo chat."));
            return;
        }

        if(p == member){
            p.sendMessage(ChatGroups.format("&cNon puoi riomovere te stesso da un gruppo chat."));
            return;
        }

        if(control.isOwner(member, name)){
            p.sendMessage(ChatGroups.format("&cImpossibile rimuovere il player, esso risulta operatore di questa chat."));
            return;
        }

        control.removeMember(member, name);
        p.sendMessage(ChatGroups.format("&eHai rimosso &f" + member.getName() + " &eal gruppo chat &f" + name + "&a."));
        if(member.isOnline()){
            Player memberOnline = Bukkit.getPlayer(member.getUniqueId());
            if(memberOnline != null){
                memberOnline.sendMessage(ChatGroups.format("&aSei stato rimosso dal gruppo chat &f" + name + "&a."));
            }
        }
    }
}
