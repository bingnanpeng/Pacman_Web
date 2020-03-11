package edu.rice.comp504.model.gameobj;

/**
 * Null object.
 */
public class NullObj extends EdibleObj {
    private static NullObj instance;

    /**
     * Null Object.
     */
    private NullObj() {
    }

    /**
     * Get null object.
     *
     * @return null object.
     */
    public static NullObj getInstance() {
        if (instance == null) {
            instance = new NullObj();
        }
        return instance;
    }
}
