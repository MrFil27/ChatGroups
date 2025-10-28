package io.github.mrfil27.chatgroups.api.manager;

import org.bukkit.entity.Player;

public interface ActionManager {

    void scream(Player player, String message);
    void whisper(Player player, String message);
    void executeCommand(Player player, String message);

}
