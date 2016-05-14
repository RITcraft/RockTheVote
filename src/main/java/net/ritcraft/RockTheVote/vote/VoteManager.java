package net.ritcraft.RockTheVote.vote;

import net.ritcraft.RockTheVote.Language;
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

    /**
     * Load votes from the plugin config.
     */
    public void loadVotes() {
        // Unload currently loaded votes
        disableVotes();

        // Load votes from config
        ConfigurationSection
                sectionVoting = RockTheVote.getConfigManager().getSectionVoting(),
                sectionDefaults = sectionVoting.getConfigurationSection("defaults"),
                sectionVotes = sectionVoting.getConfigurationSection("votes");

        // Load vote defaults
        String defDesc, defPassMessage, defBarColorStr, defBarStyleStr;
        defDesc = defPassMessage = defBarColorStr = defBarStyleStr = null;
        int defExpireTime = 0;
        double defPassPercent = 0;
        if (sectionDefaults != null) {
            defDesc = sectionDefaults.getString(KEY_DESCRIPTION);
            defExpireTime = sectionDefaults.getInt(KEY_EXPIRE_TIME);
            defPassPercent = sectionDefaults.getDouble(KEY_PASS_PERCENT);
            defPassMessage = sectionDefaults.getString(KEY_PASS_MESSAGE);
            defBarColorStr = sectionDefaults.getString(KEY_BAR_COLOR);
            defBarStyleStr = sectionDefaults.getString(KEY_BAR_STYLE);
        }

        // Load votes
        if (sectionVotes != null) {
            for (String voteName : sectionVotes.getKeys(false)) {
                ConfigurationSection sectionCurVote = sectionVotes.getConfigurationSection(voteName);

                String desc = sectionCurVote.getString(KEY_DESCRIPTION, defDesc);
                int expireTime = sectionCurVote.getInt(KEY_EXPIRE_TIME, defExpireTime);
                double passPercent = sectionCurVote.getDouble(KEY_PASS_PERCENT, defPassPercent);
                String passMessage = sectionCurVote.getString(KEY_PASS_MESSAGE, defPassMessage);
                String barColorStr = sectionCurVote.getString(KEY_BAR_COLOR, defBarColorStr);
                String barStyleStr = sectionCurVote.getString(KEY_BAR_STYLE, defBarStyleStr);
                List<String> commands = sectionCurVote.getStringList(KEY_COMMANDS);

                BarColor barColor;
                try {
                    barColor = BarColor.valueOf(barColorStr);
                } catch (IllegalArgumentException e) {
                    RockTheVote.getInstance().getLogger().warning(Language.getInvalidBarColor(barColorStr));
                    continue;
                }

                BarStyle barStyle;
                try {
                    barStyle = BarStyle.valueOf(barStyleStr);
                } catch (IllegalArgumentException e) {
                    RockTheVote.getInstance().getLogger().warning(Language.getInvalidBarStyle(barStyleStr));
                    continue;
                }

                votes.put(voteName.toLowerCase(), new Vote(
                        Language.format(voteName), expireTime, passPercent, commands,
                        Language.format(desc), Language.format(passMessage), barColor, barStyle));
            }
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

    /**
     * Disable all of the loaded votes
     */
    public void disableVotes() {
        for (String s : votes.keySet()) {
            votes.get(s).disable();
            votes.remove(s);
        }
    }
}
