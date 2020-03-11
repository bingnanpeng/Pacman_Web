package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.environment.AllEnv;

/**
 * Energizer.
 */
public class Energizer extends EdibleObj {
    public Energizer() {
        this.setScore(50);
    }

    /**
     * Set energizer as eaten.
     *
     * @param player player
     * @param allEnv game environment
     */
    @Override
    public void setEaten(Player player, AllEnv allEnv) {
        player.addScore(this.getScore());
        allEnv.setFrightenedTimer(allEnv.getMaxFrightenedTimer());
    }
}
