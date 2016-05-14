package net.ritcraft.RockTheVote;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Configuration for this plugin's messages.
 */
public class Language {
    private static final String NOT_FOUND = ChatColor.RED + "[Lang: <key>]";
    private static final String NOT_FOUND_KEY = "key";

    private static final YamlConfiguration lang = new YamlConfiguration();

    /**
     * Load the language config.
     */
    public static void load() {
        RockTheVote plugin = RockTheVote.getInstance();
        plugin.saveResource("lang.yml", false);

        File langFile = new File(plugin.getDataFolder(), "lang.yml");
        try {
            lang.load(langFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            plugin.getLogger().warning("Error loading lang.yml: " + e.getMessage());
        }
    }

    /**
     * Format a String given variables and values
     *
     * @param str    The String to format.
     * @param varVal The variables and values.
     * @return Returns the formatted String
     */
    public static String format(String str, String... varVal) {
        assert varVal.length % 2 == 0 : "varVal must have an even number of elements";

        for (int i = 0; i < varVal.length; i += 2) {
            str = str.replace("<" + varVal[i] + ">", varVal[i + 1]);
        }
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    /**
     * Get a nullable string from the language configuration.
     *
     * @param key    The language key to get.
     * @param varVal The variables and values for the language entry.
     * @return Returns the string entry, with values substituted for its variables.
     */
    private static String getNullable(String key, String... varVal) {
        String entry = lang.getString(key);
        return entry == null ? null : format(entry, varVal);
    }

    /**
     * Get a string from the language configuration.
     *
     * @param key    The language key to get.
     * @param varVal The variables and values for the language entry.
     * @return Returns the string entry, with values substituted for its variables.
     */
    private static String get(String key, String... varVal) {
        String entry = getNullable(key, varVal);
        return entry == null ? format(NOT_FOUND, NOT_FOUND_KEY, key) : entry;
    }

    /**
     * Useful method to convert an integer to a String.
     *
     * @param integer The integer to convert to a String.
     * @return Returns the integer as a String.
     */
    private static String s(int integer) {
        return Integer.toString(integer);
    }


    public static String getVoteBarTitle(String voteName, String description, int count, int total) {
        return get("vote-bar-title", "vote", voteName, "desc", description, "count", s(count), "total", s(total));
    }

    public static String getInvalidBarColor(String value) {
        return get("invalid-bar-color", "value", value);
    }

    public static String getInvalidBarStyle(String value) {
        return get("invalid-bar-style", "value", value);
    }

    public static String getVoteCast(String vote) {
        return get("vote-cast", "vote", vote);
    }

    public static String getVoteNotFound(String vote) {
        return get("vote-not-found", "vote", vote);
    }

    public static String getVotePlayerOnly() {
        return get("vote-player-only");
    }
}
