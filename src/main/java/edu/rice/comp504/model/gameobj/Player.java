package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.strategy.skill.ISkillStrategy;
import edu.rice.comp504.model.strategy.update.AUpdateStrategy;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;
import java.util.List;

/**
 * Player.
 */
public class Player extends MovingObj {
    private int id;
    private int life; // CONSTANCE life and remaining life.
    private int ghostEatenNum; //Record double score when eating ghost.
    //    private Direction nextDirection; //record next direction to turn.
    private List<ISkillStrategy> skillStrategy;

    /**
     * Constructor.
     *
     * @param speed          player's speed
     * @param location       player's location
     * @param updateStrategy player's update strategy
     * @param id             player's id
     * @param life           player's life number
     * @param skillStrategy  player's skill strategy
     */
    public Player(int speed, Point location, AUpdateStrategy updateStrategy, int id, int life, List<ISkillStrategy> skillStrategy) {
        super(speed, location, updateStrategy);
        this.setLocation(Loc2LocHelper.toCanvasLoc(location));
        this.id = id;
        this.life = life;
        this.ghostEatenNum = 0;
        this.skillStrategy = skillStrategy;
    }

    /**
     * Player update.
     */
    @Override
    public void update() {
        Point oldPos = this.getLocation();
        Point newPos;
        if (this.getDirection() == Direction.LEFT) {
            newPos = new Point(oldPos.x - this.getSpeed(), oldPos.y);
        } else if (this.getDirection() == Direction.RIGHT) {
            newPos = new Point(oldPos.x + this.getSpeed(), oldPos.y);
        } else if (this.getDirection() == Direction.DOWN) {
            newPos = new Point(oldPos.x, oldPos.y + this.getSpeed());
        } else {
            newPos = new Point(oldPos.x, oldPos.y - this.getSpeed());
        }
        newPos.x = (newPos.x - 10 + 560) % 560 + 10;
        this.setLocation(newPos);
    }

    /**
     * Reset player.
     *
     * @param allEnv game environment
     */
    @Override
    public void reset(AllEnv allEnv) {
        this.setLocation(Loc2LocHelper.toCanvasLoc(allEnv.getInitPlace().getPlayerLoc()[0]));
        this.setDirection(Direction.LEFT);
    }


    // TODO getter and setter of score, life.

    /**
     * Add score.
     *
     * @param score add player's score
     */
    public void addScore(int score) {
        setScore(getScore() + score);
    }

    /**
     * Get caught and lose one life.
     */
    public void caught(AllEnv allEnv) {
        this.life--;
        if (this.life == 0) {
            // TODO: game over
            allEnv.setGameover(true);
        } else {
            reset(allEnv);
        }
    }


    /**
     * Get caught and lose one life.
     */
    public void eat(AllEnv allEnv) {
        Point curUnitPos = Loc2LocHelper.toUnitLoc(this.getLocation());
        int x = curUnitPos.x;
        int y = curUnitPos.y;
        EdibleObj[][] temp = allEnv.getAllEdibles();
        if (temp[x][y] != NullObj.getInstance()) {
            temp[x][y].setEaten(this, allEnv);
            temp[x][y] = NullObj.getInstance();
            Point emptyPlace = new Point(x, y);
            allEnv.setEmptyPlace(emptyPlace);
        }
    }


    /**
     * Detect collision in the game.
     *
     * @param env game environment
     * @return if there is collision
     */
    @Override
    public boolean detectCollision(AllEnv env) {
        Point oldPos = this.getLocation();
        Direction d = getDirection();
        Point newPos = new Point();

        Point oldUnitLoc = Loc2LocHelper.toUnitLoc(oldPos);
        int unitX = oldUnitLoc.x;
        int unitY = oldUnitLoc.y;

        int row = env.getImmutableEnv().getAllWalls().length;
        int col = env.getImmutableEnv().getAllWalls()[0].length;

        if (d == Direction.LEFT) {
            unitX = (unitX - 1 + row) % row;
        } else if (d == Direction.RIGHT) {
            unitX = (unitX + 1) % row;
        } else if (d == Direction.DOWN) {
            unitY = (unitY + 1) % col;
        } else {
            unitY = (unitY - 1 + col) % col;
        }

        if (env.getImmutableEnv().getAllWalls()[unitX][unitY] || env.getImmutableEnv().getAllGates()[unitX][unitY]) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Detect inner collision.
     *
     * @param other moving object
     * @return if there is inner collision
     */
    @Override
    public boolean innerCollision(MovingObj other) {
        Point playerPos = this.getLocation();
        Point ghostPos = other.getLocation();
        if (BFSHelper.distanceManhattan(playerPos, ghostPos) < 20) {
            return true;
        }
        return false;
    }

    /**
     * Get player id.
     *
     * @return player id
     */
    public int getId() {
        return id;
    }

    /**
     * Get skill strategy.
     *
     * @return player's skill strategy
     */
    public List<ISkillStrategy> getSkillStrategy() {
        return skillStrategy;
    }

    /**
     * Get player's life.
     *
     * @return player's life number
     */
    public int getLife() {
        return life;
    }
}
