package io.github.mrfil27.chatgroups.commands.groups;

import io.github.mrfil27.chatgroups.ChatGroups;
import io.github.mrfil27.chatgroups.commands.SubCommand;
import io.github.mrfil27.chatgroups.utils.Configs;
import org.bukkit.entity.Player;

import static io.github.mrfil27.chatgroups.ChatGroups.format;

public class Reload extends SubCommand {

    private final ChatGroups plugin;

    public Reload(ChatGroups plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Ricarica la configurazione";
    }

    @Override
    public String getSyntax() {
        return "/cg reload";
    }

    @Override
    public void perform(Player p, String[] args) {
        if(!p.hasPermission("cg.admin")){
            p.sendMessage(format("&cNon hai il permesso di eseguire questo comando."));
            return;
        }

        if(args.length != 1){
            p.sendMessage(format("&cSintassi errata, usa &7/cg reload"));
            return;
        }

        plugin.reloadConfig();

        if(Configs.checkRealisticDistance(plugin)){
            p.sendMessage(format("&cSi è verificato un errore, controlla i log per visualizzarlo."));
            return;
        }

        ChatGroups.getConfigCached().put("cg-def-config", plugin.getConfig());

        p.sendMessage(format("&f[CG] &aLa configurazione è stata ricaricata."));
    }
}
