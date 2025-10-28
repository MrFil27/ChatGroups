package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import io.github.mrfil27.chatgroups.utils.GroupControl;
import org.bukkit.entity.Player;

public class Toggle extends SubCommand {

    private final ChatGroups plugin;

    public Toggle(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public String getDescription() {
        return "Attiva la chat di un gruppo per player";
    }

    @Override
    public String getSyntax() {
        return "/cg toggle <chat>";
    }

    @Override
    public void perform(Player p, String[] args) {
        if(args.length != 2 && args[0].equalsIgnoreCase(getName())){
            p.sendMessage(ChatGroups.format("&cSintassi errata, usa &7/cg toggle <chat>"));
            return;
        }

        String chat = args[1];
        GroupControl control = new GroupControl(plugin);

        if(control.hasAnotherChatToggled(p, chat)){
            String activeChat = GroupControl.getActiveChat(p);
            p.sendMessage(ChatGroups.format("&cHai già una chat attiva:\n&7- " + activeChat));
            return;
        }

        if(!control.groupExists(chat)){
            p.sendMessage(ChatGroups.format("&cIl gruppo indicato risulta inesistente."));
            return;
        }

        if(!control.isMember(p, chat)){
            p.sendMessage(ChatGroups.format("&cNon fai parte della chat indicata."));
            return;
        }

        control.toggleChat(p, chat);
    }
}
