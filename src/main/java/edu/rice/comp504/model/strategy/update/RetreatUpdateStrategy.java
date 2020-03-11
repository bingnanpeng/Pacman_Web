package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * Retreat update strategy.
 */
public class RetreatUpdateStrategy extends AUpdateStrategy {

    /**
     * Constructor.
     */
    public RetreatUpdateStrategy() {
    }

    /**
     * Get type.
     *
     * @return type name.
     */
    public String getType() {
        return "retreat";
    }

    /**
     * Update state.
     *
     * @param self moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {

        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());
        Point tarUnitPos = AUpdateStrategy.getTarUnitPos(curUnitPos, others);
        Point nextStep =
            BFSHelper.nextEscapeMove(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos);
        AUpdateStrategy.setNextDirection(self, curUnitPos, nextStep, allEnv);

    }
}
