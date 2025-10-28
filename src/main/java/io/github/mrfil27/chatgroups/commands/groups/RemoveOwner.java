package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.IOException;

public class RemoveOwner extends SubCommand {

    private final ChatGroups plugin;

    public RemoveOwner(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "removeowner";
    }

    @Override
    public String getDescription() {
        return "Rimuovi un operatore di una chat";
    }

    @Override
    public String getSyntax() {
        return "/cg removeowner <chat> <owner>";
    }

    @Override
    public void perform(Player p, String[] args) throws IOException {
        if(!p.hasPermission("cg.owner") || !p.hasPermission("cg.admin")){
            p.sendMessage(ChatGroups.format("&cNon hai il permesso di eseguire questo comando."));
            return;
        }

        if(args.length != 3 && args[0].equalsIgnoreCase(getName())){
            p.sendMessage(ChatGroups.format("&cSintassi errata, usa &7/cg help"));
            return;
        }

        GroupControl control = new GroupControl(plugin);
        String chat = args[1];

        if(!control.groupExists(chat)){
            p.sendMessage(ChatGroups.format("&cLa chat indicata risulta inesistente."));
            return;
        }

        if(!p.hasPermission("cg.admin") || !control.isOwner(p, chat)){
            p.sendMessage(ChatGroups.format("&cNon risulti proprietario di questa chat."));
            return;
        }

        String ownerName = args[2];
        OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerName);

        if(!control.isOwner(owner, chat)){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato non e un operatore della chat."));
            return;
        }

        if(p == owner){
            p.sendMessage(ChatGroups.format("&cNon puoi riomuovere te stesso a una chat."));
            return;
        }

        control.removeOwner(owner, chat);
        p.sendMessage(ChatGroups.format("&eHai rimosso &f" + owner.getName() + " &edagli operatori della chat &f" + chat + "&e."));
        if(owner.isOnline()){
            Player ownerOnline = Bukkit.getPlayer(owner.getUniqueId());
            if(ownerOnline != null){
                ownerOnline.sendMessage(ChatGroups.format("&eSei stato rimosso dagli operatori della chat &f" + chat + "&e."));
            }
        }

    }
}
