package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Create extends SubCommand {

    private final ChatGroups plugin;

    public Create(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Crea un gruppo";
    }

    @Override
    public String getSyntax() {
        return "/cg create <name> <owner>";
    }

    @Override
    public void perform(Player p, String[] args) {
        if(!p.hasPermission("cg.admin")){
            p.sendMessage(ChatGroups.format("&cNon hai il permesso di eseguire questo comando."));
            return;
        }

        if(args.length != 3 && args[0].equalsIgnoreCase(getName())){
            p.sendMessage(ChatGroups.format("&cSintassi errata, usa &7/cg help"));
            return;
        }

        String name = args[1];

        GroupControl control = new GroupControl(plugin);

        if(control.groupExists(name)){
            p.sendMessage(ChatGroups.format("&cIl nome indicato è già assegnato a un altro gruppo."));
            return;
        }

        String playerName = args[2];
        Player owner = Bukkit.getPlayer(playerName);

        if(owner == null){
            p.sendMessage(ChatGroups.format("&cIl giocatore indicato risulta indisponibile."));
            return;
        }

        if(control.isPlayerDistant(p, owner)){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato è troppo distante."));
            return;
        }

        control.createChatGroup(owner, name);
        p.sendMessage(ChatGroups.format("&aIl gruppo chat &f" + name + " &aè stato creato correttamente."));
        owner.sendMessage(ChatGroups.format("&aSei stato aggiunto come owner nel gruppo chat &f" + name + "&a."));
    }
}
