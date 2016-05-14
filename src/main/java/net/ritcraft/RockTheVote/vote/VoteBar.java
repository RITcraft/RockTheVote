package net.ritcraft.RockTheVote.vote;

import net.ritcraft.RockTheVote.Language;
import net.ritcraft.RockTheVote.RockTheVote;
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

            bossBar.setTitle(Language.getVoteBarTitle(vote.getName(),
                    vote.getDescription(), vote.getVoteCount(), vote.getVotesRequired()));
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
