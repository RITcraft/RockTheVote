/*
 * Copyright (c) 2016 RITcraft
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.ritcraft.RockTheVote;

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
    }

    @Override
    public void onDisable() {

    }
}
