package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.environment.AllEnv;

/**
 * Dot.
 */
public final class Dot extends EdibleObj {
    public Dot() {
        this.setScore(10);
    }

    /**
     * Set player as eaten.
     *
     * @param player player
     * @param allEnv game environment
     */
    @Override
    public void setEaten(Player player, AllEnv allEnv) {
        super.setEaten(player, allEnv);
        allEnv.setDotNums(allEnv.getDotNums() - 1);
    }
}
