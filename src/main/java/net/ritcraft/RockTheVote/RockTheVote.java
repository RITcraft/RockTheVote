/*
 * Copyright (c) 2016 RITcraft
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.ritcraft.RockTheVote;

import net.ritcraft.RockTheVote.commands.CommandManager;
import net.ritcraft.RockTheVote.vote.VoteManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * RockTheVote.java
 * <p>
 * This plugin allows users to trigger certain actions on the server based on the popular vote.
 *
 * @author Justin W. Flory
 * @version 2016.05.09.v1
 */
public class RockTheVote extends JavaPlugin {
    private static RockTheVote instance;
    private static Config configManager;
    private static VoteManager voteManager;

    /**
     * Get an instance of RockTheVote
     *
     * @return Returns an instance of RockTheVote
     */
    public static RockTheVote getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Create managers
        configManager = new Config();
        voteManager = new VoteManager();

        // Register commands
        new CommandManager().registerCommands();
    }

    @Override
    public void onDisable() {

    }

    /**
     * Get this plugin's config manager.
     *
     * @return Returns this plugin's config manager.
     */
    public static Config getConfigManager() {
        return configManager;
    }

    /**
     * Get the vote manager.
     *
     * @return Returns the vote manager.
     */
    public static VoteManager getVoteManager() {
        return voteManager;
    }
}
