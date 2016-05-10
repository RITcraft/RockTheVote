package net.ritcraft.RockTheVote;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a vote option. Keeps track of which players voted for this option.
 */
public class Vote implements Runnable {
    private static final int TASK_NOT_RUNNING = -1;

    private final Set<UUID> votes = new HashSet<>(); // The votes cast.
    private final long expireTicks; // Time until vote resets. Zero if never resets.
    private final double passPercent; // The percent of online players required to pass.
    private final String command; // Command executed if the vote passes.
    private final String message; // Message broadcasted if the vote passes.

    private int expireTaskID = TASK_NOT_RUNNING;

    public Vote(long expireTicks, double passPercent, String command, String message) {
        this.expireTicks = expireTicks;
        this.passPercent = passPercent;
        this.command = command;
        this.message = message;
    }

    /**
     * Updates the vote. Checks if enough people have voted, etc.
     */
    public void update() {

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
    }

    public void passVote() {

    }

    public void resetVote() {

    }

    /**
     * Start/restart the expiration countdown
     */
    private void startExpiration() {
        // Stop the currently running task
        cancelExpiration();

        // Start new task
        if (expireTicks > 0) {
            expireTaskID = Bukkit.getScheduler().scheduleSyncDelayedTask(
                    RockTheVote.getInstance(), this, expireTicks);
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
