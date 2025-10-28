package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;

public class AddOwner extends SubCommand {

    private final ChatGroups plugin;

    public AddOwner(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "addowner";
    }

    @Override
    public String getDescription() {
        return "Aggiungi un operatore della chat";
    }

    @Override
    public String getSyntax() {
        return "/cg addowner <chat> <player>";
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
        Player owner = Bukkit.getPlayer(ownerName);

        if(owner == null){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato risulta indisponibile."));
            return;
        }

        if(control.isPlayerDistant(p, owner)){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato è troppo distante."));
            return;
        }

        if(control.isOwner(owner, chat)){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato è già un operatore della chat."));
            return;
        }

        if(p == owner){
            p.sendMessage(ChatGroups.format("&cNon puoi aggiungere te stesso a una chat."));
            return;
        }

        control.addOwner(owner, chat);
        p.sendMessage(ChatGroups.format("&aHai aggiunto &f" + owner.getName() + " &acome operatore della chat &e" + chat + "&a."));
        owner.sendMessage(ChatGroups.format("&aSei stato aggiunto come operatore nella chat &e" + chat + "&a."));
    }
}
