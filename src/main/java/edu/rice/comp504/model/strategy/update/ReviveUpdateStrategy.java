package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * Revive Strategy.
 */
public class ReviveUpdateStrategy extends AUpdateStrategy {


    /**
     * Get strategy name.
     *
     * @return strategy name.
     */
    public String getType() {
        return "revive";
    }

    /**
     * Update revive strategy.
     *
     * @param self moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {
        self.setSpeed(15);
        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());
        Point tarUnitPos = allEnv.getInitPlace().getGhostLoc()[0];
        Point nextStep =
            BFSHelper.nextChaseMove(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos);
        AUpdateStrategy.setNextDirection(self, curUnitPos, nextStep, allEnv);

    }
}
