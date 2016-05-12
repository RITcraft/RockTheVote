package net.ritcraft.RockTheVote.vote;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

/**
 * Shows a vote progress as a boss health bar.
 */
public class VoteBar {
    private final BossBar bossBar;
    private final Vote vote;

    public VoteBar(Vote vote, BarColor color, BarStyle style) {
        bossBar = Bukkit.createBossBar(null, color, style);
        this.vote = vote;
    }

    /**
     * Update the vote bar's text / progress.
     */
    public void update() {
        int voteCount = vote.getVoteCount();
        if (voteCount > 0) {
            double progress = voteCount / (double) vote.getVotesRequired();

            bossBar.setTitle(vote.voteDescription + " (" + vote.getVoteCount() + "/" + vote.getVotesRequired() + ")"); // TODO Don't hardcode title
            bossBar.setProgress(progress);
            for (Player p : Bukkit.getOnlinePlayers()) {
                bossBar.addPlayer(p);
            }
            bossBar.setVisible(true);
        } else {
            bossBar.removeAll();
            bossBar.setVisible(false);
        }
    }

    public void disable() {
        bossBar.removeAll();
        bossBar.setVisible(false);
    }
}
