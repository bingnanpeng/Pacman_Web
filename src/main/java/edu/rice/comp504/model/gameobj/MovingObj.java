package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.command.IGameCmd;
import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.strategy.update.AUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Moving Object.
 */
public abstract class MovingObj extends GameObj implements PropertyChangeListener {
    private int speed; // TODO
    private Direction direction;
    private Point location;
    private boolean invincible;

    private boolean willTurn;
    private Direction newDirection;

    private AUpdateStrategy updateStrategy;

    /**
     * Constructor.
     *
     * @param speed          moving object speed
     * @param location       moving object location
     * @param updateStrategy object strategy
     */
    public MovingObj(int speed, Point location, AUpdateStrategy updateStrategy) {
        this.speed = speed;
        this.direction = Direction.LEFT;
        this.location = location;
        this.invincible = false;
        this.willTurn = false;
        this.newDirection = null;
        this.updateStrategy = updateStrategy;
    }

    // TODO Point of location

    /**
     * Update.
     */
    public abstract void update();

    // TODO getter and setter of attributes.

    // return to its original position

    /**
     * Reset environment.
     *
     * @param allEnv game environment
     */
    public abstract void reset(AllEnv allEnv);

    /**
     * Detect collision.
     *
     * @param env game environment
     * @return if there is a collision
     */
    public abstract boolean detectCollision(AllEnv env);

    /**
     * Detect inner collision.
     *
     * @param other moving object
     * @return if there is collision between moving objects
     */
    public abstract boolean innerCollision(MovingObj other);

    /**
     * Direction.
     */
    public enum Direction {
        LEFT(0), UP(1), RIGHT(2), DOWN(3);
        private final int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public Direction clockwiseTurn() {
            return Direction.values()[(this.value + 1) % 4];
        }

        public Direction anticlockwiseTurn() {
            return Direction.values()[(this.value + 3) % 4];
        }
    }

    /**
     * Set moving object's location.
     *
     * @param location moving object's location
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * Get moving object's location.
     *
     * @return moving object's location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Set moving object's speed.
     *
     * @param speed moving object's speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Get moving object's speed.
     *
     * @return moving object's speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set moving object's direction.
     *
     * @param d set moving object's direction
     */
    public void setDirection(Direction d) {
        this.direction = d;
    }

    /**
     * Get moving object's direction.
     *
     * @return moving object's direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * If invincible skill is used.
     *
     * @return if invincible skill is used
     */
    public boolean isInvincible() {
        return invincible;
    }

    /**
     * Set a player's skill as invincible.
     *
     * @param invincible cannot be eat
     */
    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    /**
     * Set if a player will turn.
     *
     * @param willTurn if a player will turn
     */
    public void setWillTurn(boolean willTurn) {
        this.willTurn = willTurn;
    }

    /**
     * Get if a player will turn.
     *
     * @return if a player will turn
     */
    public boolean isWillTurn() {
        return willTurn;
    }

    /**
     * Set player new direction.
     *
     * @param newDirection New direction
     */
    public void setNewDirection(Direction newDirection) {
        this.newDirection = newDirection;
    }

    /**
     * Get player's new direction.
     *
     * @return player's new direction
     */
    public Direction getNewDirection() {
        return newDirection;
    }


    /**
     * Get updatestrategy.
     *
     * @return updatestrategy
     */
    public AUpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    /**
     * Set updateStrategy.
     *
     * @param updateStrategy updateStrategy
     */
    public void setUpdateStrategy(AUpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    /**
     * Update state of the moving object when the property change event occurs by executing the command.
     *
     * @param evt property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        IGameCmd cmd = (IGameCmd) evt.getNewValue();
        cmd.execute(this);
    }
}
