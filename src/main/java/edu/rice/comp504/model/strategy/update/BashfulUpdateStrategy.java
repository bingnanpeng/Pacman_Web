package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * When player charges it, it will wander for a period.
 * When ghost is away from pacman (>8), it acts like shadow
 * When ghost is near the pacman, it moves randomly
 */
public class BashfulUpdateStrategy extends AUpdateStrategy {

    /**
     * Constructor.
     */
    public BashfulUpdateStrategy() {
    }

    /**
     * Get strategy type.
     *
     * @return type name.
     */
    public String getType() {
        return "bashful";
    }

    /**
     * Update state
     *
     * @param self moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {

        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());
        Point tarUnitPos = AUpdateStrategy.getTarUnitPos(curUnitPos, others);

        // The special step choice of bashful ghost
        Point nextStep;
        if (BFSHelper.distanceBFS(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos) > 8) {
            nextStep = BFSHelper.nextChaseMove(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos);
        } else {
            nextStep = BFSHelper.nextRandomMove(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos);
        }
        AUpdateStrategy.setNextDirection(self, curUnitPos, nextStep, allEnv);

    }


}
