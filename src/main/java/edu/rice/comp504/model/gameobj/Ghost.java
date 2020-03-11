package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;
import edu.rice.comp504.model.strategy.update.AUpdateStrategy;

import java.awt.*;

/**
 * Ghost.
 */
public final class Ghost extends MovingObj {
    private int duration = 0; // duration to judge update behavior
    private boolean isEaten;
    private boolean isHome;
    private int viewRange;

    // private String color;

    //private final IChaseStrategy chaseStrategy;
    private AUpdateStrategy chase;
    private AUpdateStrategy retreat;
    private AUpdateStrategy wander;
    private AUpdateStrategy revive;


    /**
     * Constructor.
     *
     * @param chase chase strategy
     */
    public Ghost(int speed, Point location, AUpdateStrategy retreat, AUpdateStrategy wander, AUpdateStrategy chase, AUpdateStrategy revive) {
        super(speed, location, wander);
        this.setLocation(Loc2LocHelper.toCanvasLoc(location));
        super.setInvincible(true);
        this.chase = chase;
        this.retreat = retreat;
        this.wander = wander;
        this.revive = revive;
        this.viewRange = 10;
    }

//    public Ghost(IChaseStrategy chaseStrategy) throws IllegalArgumentException {
//        //this.chaseStrategy = chaseStrategy;
//
//        switch (chaseStrategy.getType()) {
//            case "shadow":
//                this.color = "red";
//                break;
//            case "speedy":
//                this.color = "pink";
//                break;
//            case "bashful":
//                this.color = "cyan";
//                break;
//            case "pokey":
//                this.color = "chocolate";
//                break;
//            default:
//                throw new IllegalArgumentException("wrong chase strategy type");
//        }
//    }


    /**
     * Ghost viewrange.
     *
     * @param viewRange viewRange
     */
    public void setViewRange(int viewRange) {
        this.viewRange = viewRange;
    }

    /**
     * get viewrange.
     */
    public int getViewRange() {
        return viewRange;
    }

    /**
     * doc.
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
        this.setLocation(newPos);
    }

    /**
     * doc.
     *
     * @param env doc.
     * @return doc.
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

        if (env.getImmutableEnv().getAllWalls()[unitX][unitY]) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * doc.
     *
     * @return doc.
     */
    @Override
    public boolean innerCollision(MovingObj other) {
        Point ghostPos = this.getLocation();
        Point playerPos = other.getLocation();
        if (BFSHelper.distanceManhattan(playerPos, ghostPos) < 20) {
            return true;
        }
        return false;
    }

    /**
     * doc.
     *
     * @param strategy doc.
     */
    public void updateStrategy(String strategy) {
        if (strategy.equals("chase")) {
            this.setUpdateStrategy(chase);
        } else if (strategy.equals("retreat")) {
            this.setUpdateStrategy(retreat);
        } else if (strategy.equals("revive")) {
            this.setUpdateStrategy(revive);
        } else {
            this.setUpdateStrategy(wander);
        }
    }

    public String getUpdateStrategyType() {
        return super.getUpdateStrategy().getType();
    }

    /**
     * doc.
     */
    private void leaveHome() {
    }

    /**
     * doc.
     */
    @Override
    public void reset(AllEnv allEnv) {

    }


}
