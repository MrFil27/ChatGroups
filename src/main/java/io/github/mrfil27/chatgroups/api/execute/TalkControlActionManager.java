package io.github.mrfil27.chatgroups.api.execute;

import io.github.mrfil27.chatgroups.api.manager.ActionManager;
import io.github.mrfil27.chatgroups.utils.TalkControl;
import org.bukkit.entity.Player;

public class TalkControlActionManager implements ActionManager {
    @Override
    public void scream(Player player, String message) {
        TalkControl.scream(player, message);
    }

    @Override
    public void whisper(Player player, String message) {
        TalkControl.whisper(player, message);
    }

    @Override
    public void executeCommand(Player player, String message) {
        TalkControl.executeAction(player, message);
    }
}
