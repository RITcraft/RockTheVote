package net.ritcraft.RockTheVote.commands;

import net.md_5.bungee.api.ChatColor;
import net.ritcraft.RockTheVote.RockTheVote;
import net.ritcraft.RockTheVote.vote.Vote;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Handles the toggle command.
 */
public class CmdVote extends SimpleCommand {
    private static final String
            USAGE = "/vote [vote]",
            VOTE_NOT_FOUND = ChatColor.RED + "There is no vote with the name '%s'",
            VOTE_CAST = ChatColor.GREEN + "Your vote for " + ChatColor.BLUE + "%s" + ChatColor.GREEN + " has been cast",
            PLAYER_ONLY = ChatColor.RED + "You must be a player to cast a vote";

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PLAYER_ONLY);
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            showVotes(player);
        } else {
            castVote(player, StringUtils.join(args, ' '));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    public void showVotes(Player player) {
        player.sendMessage("You're out of luck... this feature hasn't been implemented yet. :(");
        player.sendMessage("Normally you would see what votes are available when you run this command.");
    }

    public void castVote(Player player, String voteName) {
        Vote vote = RockTheVote.getVoteManager().getVote(voteName);
        if (vote == null) {
            player.sendMessage(String.format(VOTE_NOT_FOUND, voteName));
            return;
        }

        vote.castVote(player);
        player.sendMessage(String.format(VOTE_CAST, vote.getName()));
    }
}
