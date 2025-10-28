package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AddMember extends SubCommand {

    private final ChatGroups plugin;

    public AddMember(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "addmember";
    }

    @Override
    public String getDescription() {
        return "Aggiungi un membro al gruppo chat";
    }

    @Override
    public String getSyntax() {
        return "/cg addmember <group> <player>";
    }

    @Override
    public void perform(Player p, String[] args) {
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
        Player target = Bukkit.getPlayer(playerName);

        if(target == null){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato risulta inesistente."));
            return;
        }

        if(control.isPlayerDistant(p, target)){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato è troppo distante."));
            return;
        }

        if(!control.isOwner(p, name)){
            p.sendMessage(ChatGroups.format("&cNon risulti owner di questo gruppo."));
            return;
        }

        if(control.isMember(target, name)){
            p.sendMessage(ChatGroups.format("&cIl cittadino indicato fa già parte del gruppo chat."));
            return;
        }

        if(p == target){
            p.sendMessage(ChatGroups.format("&cNon puoi inserire te stesso in un gruppo chat."));
            return;
        }

        if(!control.hasMembers(name)){
            p.sendMessage(ChatGroups.format("&cNon ci sono membri in questo gruppo chat."));
            return;
        }

        control.addMember(target, name);
        p.sendMessage(ChatGroups.format("&aHai aggiunto &e" + target.getName() + " &aal gruppo chat &f" + name + "&a."));
        target.sendMessage(ChatGroups.format("&aSei stato aggiunto al gruppo chat &f" + name + "&a."));
    }
}
