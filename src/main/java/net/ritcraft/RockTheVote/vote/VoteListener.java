package net.ritcraft.RockTheVote.vote;

import net.ritcraft.RockTheVote.RockTheVote;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listens to events that affect votes.
 */
public class VoteListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        RockTheVote.getVoteManager().revokeVotes(e.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        RockTheVote.getVoteManager().updateVotes();
    }
}
