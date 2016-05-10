package net.ritcraft.RockTheVote.commands;

import net.ritcraft.RockTheVote.RockTheVote;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registers commands, and handles execution and tab completion.
 */
public class CommandManager implements CommandExecutor, TabCompleter {
    private Map<String, SimpleCommand> commandMap = new HashMap<>();

    /**
     * Create a new instance of the command manager.
     */
    public CommandManager() {
        commandMap.put("vote", new CmdVote());
        commandMap.put("poll", new CmdPoll());
    }

    /**
     * Register this as the command executor for all FastCraft+ commands.
     */
    public void registerCommands() {
        RockTheVote rtv = RockTheVote.getInstance();
        for (String command : commandMap.keySet()) {
            rtv.getCommand(command).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        // Get the command if it exists, otherwise return false
        SimpleCommand command = commandMap.get(c.getName());
        if (command == null) return false;

        // Execute the command, and return its result
        return command.onCommand(sender, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command c, String s, String[] args) {
        // Get the command if it exists, otherwise return false
        SimpleCommand command = commandMap.get(c.getName());
        if (command == null) return Collections.emptyList();

        // Tab complete the command
        List<String> result = command.onTabComplete(sender, args);
        return result == null ? Collections.<String>emptyList() : result;
    }
}
