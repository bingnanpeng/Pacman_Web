package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.environment.AllEnv;

/**
 * Fruit.
 */
public class Fruit extends EdibleObj {
    private static Fruit instance;

    /**
     * Fruit.
     */
    private Fruit() {
        this.setScore(100);
    }

    /**
     * Fruit.
     *
     * @return Fruit.
     */
    public static Fruit getInstance() {
        if (instance == null) {
            instance = new Fruit();
        }
        return instance;
    }

    /**
     * If fruit exists.
     *
     * @return If fruit exists
     */
    public static boolean isExist() {
        if (instance == null) {
            return false;
        }
        return true;
    }

    /**
     * Set fruit as eaten.
     *
     * @param player player
     * @param allEnv game environment
     */
    @Override
    public void setEaten(Player player, AllEnv allEnv) {
        super.setEaten(player, allEnv);
        instance = null;
    }
}
