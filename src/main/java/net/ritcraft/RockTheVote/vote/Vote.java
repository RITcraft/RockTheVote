package net.ritcraft.RockTheVote.vote;

import net.ritcraft.RockTheVote.RockTheVote;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a vote option. Keeps track of which players voted for this option.
 */
public class Vote implements Runnable {
    private static final int TASK_NOT_RUNNING = -1;

    private final Set<UUID> votes = new HashSet<>(); // The votes cast.
    private final int expireTime; // Time until vote resets. Zero if never resets.
    private final double passPercent; // The percent of online players required to pass.
    private final VoteBar voteBar; // The vote bar displayed to players.

    public final String voteName; // The name of the vote.
    public final List<String> commands; // Commands executed if the vote passes.
    public final String voteDescription; // Message describing the vote.
    public final String passMessage; // Message broadcasted if the vote passes.

    private int expireTaskID = TASK_NOT_RUNNING;

    /**
     * Create a new vote instance.
     *
     * @param voteName        The name of the vote.
     * @param expireTime      The number of seconds until the vote expires.
     * @param passPercent     The percent of players online required to pass the vote.
     * @param commands        The commands run when the vote passes.
     * @param voteDescription A description of the vote.
     * @param passMessage     The message shown when the vote passes.
     */
    public Vote(String voteName, int expireTime, double passPercent,
                List<String> commands, String voteDescription, String passMessage,
                BarColor voteBarColor, BarStyle voteBarStyle) {
        this.voteName = voteName;
        this.expireTime = expireTime;
        this.passPercent = passPercent;
        this.commands = commands;
        this.voteDescription = voteDescription;
        this.passMessage = passMessage;

        voteBar = new VoteBar(this, voteBarColor, voteBarStyle);
    }

    /**
     * Get the name of the vote.
     */
    public String getName() {
        return voteName;
    }

    /**
     * Updates the vote. Checks if enough people have voted, etc.
     */
    public void update() {
        voteBar.update();

        // Pass the vote if enough players voted
        if (getVoteCount() >= getVotesRequired()) {
            passVote();
        }
    }

    /**
     * Cast a vote from a player.
     *
     * @param player The player casting the vote.
     * @return Returns false if the player already voted. True otherwise.
     */
    public boolean castVote(Player player) {
        UUID id = player.getUniqueId();
        if (votes.contains(id)) return false;
        votes.add(id);
        update();
        return true;
    }

    /**
     * Remove a player's vote.
     *
     * @param player The player revoking the vote.
     */
    public void revokeVote(Player player) {
        votes.remove(player.getUniqueId());
        update();
    }

    /**
     * Pass this vote.
     */
    public void passVote() {
        resetVote();

        // Broadcast message
        Bukkit.broadcastMessage(passMessage);

        // Execute commands
        for (String s : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
        }
    }

    /**
     * Reset this vote.
     */
    public void resetVote() {
        votes.clear();
        cancelExpiration();
        update();
    }

    /**
     * Get the number of votes cast.
     *
     * @return Returns the number of votes cast.
     */
    public int getVoteCount() {
        return votes.size();
    }

    /**
     * Get the number of votes required for the vote to pass.
     *
     * @return Returns the number of votes required.
     */
    public int getVotesRequired() {
        int playersOnline = Bukkit.getOnlinePlayers().size();
        return (int) Math.ceil(playersOnline * passPercent);
    }

    /**
     * Start/restart the expiration countdown
     */
    private void startExpiration() {
        // Stop the currently running task
        cancelExpiration();

        // Start new task
        if (expireTime > 0) {
            expireTaskID = Bukkit.getScheduler().scheduleSyncDelayedTask(
                    RockTheVote.getInstance(), this, expireTime * 20L);
        }
    }

    /**
     * Stop the running expiration task.
     */
    private void cancelExpiration() {
        if (expireTaskID != TASK_NOT_RUNNING) {
            Bukkit.getScheduler().cancelTask(expireTaskID);
            expireTaskID = TASK_NOT_RUNNING;
        }
    }

    /**
     * Executed when the vote expires.
     */
    public void run() {
        resetVote();
        expireTaskID = TASK_NOT_RUNNING;
    }
}
