package io.github.mrfil27.chatgroups.commands;

import org.bukkit.entity.Player;

import java.io.IOException;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract void perform(Player p, String[] args) throws IOException;

}
