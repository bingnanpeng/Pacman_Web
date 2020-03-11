package edu.rice.comp504.model.gameobj;

import java.util.Optional;

/**
 * GameObj.
 */
public abstract class GameObj {
    private String color;
    private Integer size;
    private String src;
    private String type = this.getClass().getSimpleName().toLowerCase();
    private int score;

    /**
     * Get game object's color.
     * @return game object's color
     */
    public String getColor() {
        return color;
    }

    /**
     * Set game object's color.
     * @param color set game object's color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get game object's size.
     * @return game object's size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Set game object's size.
     * @param size game object's size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Get game objects source.
     * @return game objects source
     */
    public String getSrc() {
        return src;
    }

    /**
     * Set source.
     * @param src game image source
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Get type.
     * @return Get game object's type
     */
    public String getType() {
        return type;
    }

    /**
     * Get score.
     * @return game object's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set score.
     * @param score set game object's score
     */
    protected void setScore(int score) {
        if ("player".equals(type)) {
            this.score = score;
        }
        this.score = score;
    }
}
