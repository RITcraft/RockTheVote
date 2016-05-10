package net.ritcraft.RockTheVote.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Handles the toggle command.
 */
public class CmdVote extends SimpleCommand {
    private static final String USAGE = "/vote [vote]";

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, String[] args) {

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }


}
