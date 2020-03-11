package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * Abstract class of update strategy.
 */
public abstract class AUpdateStrategy {
    private String type;

    {
        String classname = this.getClass().getSimpleName();
        type = classname.substring(0, classname.indexOf("UpdateStrategy")).toLowerCase();
    }

    /**
     * Get strategy type.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Update state.
     *
     * @param self moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    public abstract void updateState(MovingObj self, AllEnv allEnv, MovingObj... others);

    /**
     * Get target unit position.
     *
     * @param curUnitPos current location.
     * @param others moving objects.
     * @return target location.
     */
    static Point getTarUnitPos(Point curUnitPos, MovingObj... others) {
        if (others.length == 1) {
            return Loc2LocHelper.toUnitLoc(others[0].getLocation());
        } else {
            Point tar0 = Loc2LocHelper.toUnitLoc(others[0].getLocation());
            Point tar1 = Loc2LocHelper.toUnitLoc(others[1].getLocation());
            if (BFSHelper.distanceManhattan(curUnitPos, tar0) < BFSHelper.distanceManhattan(curUnitPos, tar1)) {
                return tar0;
            } else {
                return tar1;
            }
        }
    }

    /**
     * Set next direction.
     *
     * @param self moving object.
     * @param curUnitPos current position.
     * @param nextStep next position.
     * @param allEnv game environment.
     */
    static void setNextDirection(MovingObj self, Point curUnitPos, Point nextStep, AllEnv allEnv) {
        MovingObj.Direction nextDirection;
        if (nextStep.x == curUnitPos.x - 1) {
            nextDirection = MovingObj.Direction.LEFT;
        } else if (nextStep.x == curUnitPos.x + 1) {
            nextDirection = MovingObj.Direction.RIGHT;
        } else if (nextStep.y == curUnitPos.y + 1) {
            nextDirection = MovingObj.Direction.DOWN;
        } else {
            nextDirection = MovingObj.Direction.UP;
        }
        if (self.getDirection() != nextDirection) {
            self.setWillTurn(true);
            self.setNewDirection(nextDirection);
        }

        if (self.isWillTurn() && Loc2LocHelper.canTurn(self, allEnv.getImmutableEnv().getAllWalls())) {
            self.setDirection(self.getNewDirection());
            self.setWillTurn(false);
            self.setLocation(Loc2LocHelper.toCanvasLoc(curUnitPos));
        }

        if (self.detectCollision(allEnv)) {
            self.setLocation(Loc2LocHelper.toCanvasLoc(curUnitPos));
        } else {
            self.update();
        }
    }
}
