package net.ritcraft.RockTheVote.vote;

import net.ritcraft.RockTheVote.RockTheVote;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;

/**
 * Manage all of the votes.
 */
public class VoteManager {
    private static final String
            KEY_DESCRIPTION = "description",
            KEY_EXPIRE_TIME = "expire-time",
            KEY_PASS_PERCENT = "pass-percent",
            KEY_PASS_MESSAGE = "pass-message",
            KEY_BAR_COLOR = "bar-color",
            KEY_BAR_STYLE = "bar-style",
            KEY_COMMANDS = "commands";

    private final HashMap<String, Vote> votes = new HashMap<>(); // <lowercase vote name, vote>

    /**
     * Create a new instance of VoteManager. Should be instantiated after the config manager.
     */
    public VoteManager() {
        loadVotes();
    }

    public void loadVotes() {
        // Remove all votes
        for (String s : votes.keySet()) {
            votes.get(s).resetVote();
            votes.remove(s);
        }

        // Load votes from config
        ConfigurationSection
                sectionVoting = RockTheVote.getConfigManager().getSectionVoting(),
                sectionDefaults = sectionVoting.getConfigurationSection("defaults"),
                sectionVotes = sectionVoting.getConfigurationSection("votes");

        // Load vote defaults
        String defDesc = sectionDefaults.getString(KEY_DESCRIPTION);
        int defExpireTime = sectionDefaults.getInt(KEY_EXPIRE_TIME);
        double defPassPercent = sectionDefaults.getDouble(KEY_PASS_PERCENT);
        String defPassMessage = sectionDefaults.getString(KEY_PASS_MESSAGE);
        String defBarColorStr = sectionDefaults.getString(KEY_BAR_COLOR);
        String defBarStyleStr = sectionDefaults.getString(KEY_BAR_STYLE);

        // Load votes
        for (String voteName : sectionVotes.getKeys(false)) {
            ConfigurationSection sectionCurVote = sectionVotes.getConfigurationSection(voteName);

            String desc = sectionDefaults.getString(KEY_DESCRIPTION, defDesc);
            int expireTime = sectionDefaults.getInt(KEY_EXPIRE_TIME, defExpireTime);
            double passPercent = sectionDefaults.getDouble(KEY_PASS_PERCENT, defPassPercent);
            String passMessage = sectionDefaults.getString(KEY_PASS_MESSAGE, defPassMessage);
            String barColorStr = sectionDefaults.getString(KEY_BAR_COLOR, defBarColorStr);
            String barStyleStr = sectionDefaults.getString(KEY_BAR_STYLE, defBarStyleStr);
            List<String> commands = sectionDefaults.getStringList(KEY_COMMANDS);

            BarColor barColor;
            try {
                barColor = BarColor.valueOf(barColorStr);
            } catch (IllegalArgumentException e) {
                RockTheVote.getInstance().getLogger().warning("Invalid value for bar-color: " + barColorStr);
                continue;
            }

            BarStyle barStyle;
            try {
                barStyle = BarStyle.valueOf(barStyleStr);
            } catch (IllegalArgumentException e) {
                RockTheVote.getInstance().getLogger().warning("Invalid value for bar-style: " + barStyleStr);
                continue;
            }

            votes.put(voteName.toLowerCase(), new Vote(voteName, expireTime, passPercent,
                    commands, desc, passMessage, barColor, barStyle));
        }
    }

    /**
     * Get a vote from a specific name.
     *
     * @param voteName The name of the vote.
     * @return Returns the vote with the specified name.
     */
    public Vote getVote(String voteName) {
        return votes.get(voteName.toLowerCase());
    }
}
