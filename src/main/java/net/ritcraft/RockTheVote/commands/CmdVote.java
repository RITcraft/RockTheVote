package net.ritcraft.RockTheVote.commands;

import net.ritcraft.RockTheVote.Language;
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
    private static final String USAGE = "/vote [vote]";

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.getVotePlayerOnly());
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            showVotes(player, "");
        } else {
            castVote(player, StringUtils.join(args, ' '));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    public void showVotes(Player player, String startingWith) {
        player.sendMessage("You're out of luck... this feature hasn't been implemented yet. :(");
        player.sendMessage("Normally you would see what votes are available when you run this command.");
    }

    public void castVote(Player player, String voteName) {
        Vote vote = RockTheVote.getVoteManager().getVote(voteName);
        if (vote == null) {
            player.sendMessage(Language.getVoteNotFound(voteName));
            showVotes(player, voteName);
            return;
        }

        player.sendMessage(Language.getVoteCast(vote.getName()));
        vote.castVote(player);
    }
}
