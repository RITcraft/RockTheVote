package net.ritcraft.RockTheVote;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Handles access to the configuration file.
 */
public class Config {
    private final FileConfiguration config;

    /**
     * Create a new instance of Config.
     */
    public Config() {
        RockTheVote plugin = RockTheVote.getInstance();
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    /**
     * Get the voting section from the configuration.
     *
     * @return Returns the voting section.
     */
    public ConfigurationSection getSectionVoting() {
        return config.getConfigurationSection("voting");
    }

    /**
     * Get the polls section from the configuration.
     *
     * @return Returns the polls section.
     */
    public ConfigurationSection getSectionPolls() {
        return config.getConfigurationSection("polls");
    }
}
