package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;
import java.util.Random;

/**
 * Ghost wander update strategy.
 */
public class WanderUpdateStrategy extends AUpdateStrategy {
    // just use BFSHelper.method()

    public WanderUpdateStrategy() {
    }

    public String getType() {
        return "wander";
    }

    /**
     * Update state.
     *
     * @param self   moving object itself
     * @param allEnv game environment
     * @param others moving objects
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {
        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());
        int lx = allEnv.getImmutableEnv().getAllWalls().length;
        int hx = lx / 2;
        int ly = allEnv.getImmutableEnv().getAllWalls()[0].length;
        int hy = ly / 2;
        Point tarUnitPos;
        if (curUnitPos.x < hx && curUnitPos.y < hy) {
            tarUnitPos = randPlace(allEnv.getImmutableEnv().getAllWalls(), hx, lx - hx, 0, hy);
        } else if (curUnitPos.x >= hx && curUnitPos.y < hy) {
            tarUnitPos = randPlace(allEnv.getImmutableEnv().getAllWalls(), hx, lx - hx, hy, ly - hy);
        } else if (curUnitPos.x >= hx && curUnitPos.y >= hy) {
            tarUnitPos = randPlace(allEnv.getImmutableEnv().getAllWalls(), 0, hx, hy, ly - hy);
        } else {
            tarUnitPos = randPlace(allEnv.getImmutableEnv().getAllWalls(), 0, hx, 0, hy);
        }
        Point nextStep =
            BFSHelper.nextChaseMove(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos);
        AUpdateStrategy.setNextDirection(self, curUnitPos, nextStep, allEnv);

    }

    /**
     * Random places location.
     *
     * @param walls walls in game.
     * @param x     location x
     * @param dx    unit location
     * @param y     location y
     * @param dy    unit location
     * @return next location
     */
    private Point randPlace(boolean[][] walls, int x, int dx, int y, int dy) {
        Random rand = new Random();
        int rx = rand.nextInt(dx) + x;
        int ry = rand.nextInt(dy) + y;
        while (walls[rx][ry]) {
            rx = rand.nextInt(dx) + x;
            ry = rand.nextInt(dy) + y;
        }
        return new Point(rx, ry);
    }
}
