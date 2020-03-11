package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.environment.AllEnv;

/**
 * Edible object.
 */
public abstract class EdibleObj extends GameObj {
    /**
     * Set game object as eaten.
     *
     * @param player player
     * @param allEnv game environment
     */
    public void setEaten(Player player, AllEnv allEnv) {
        player.addScore(this.getScore());
    }
}
