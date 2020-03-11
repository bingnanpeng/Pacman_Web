package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * Shadow ghost has variant speed - it increases its speed as long as player eats more dots.
 */
public class ShadowUpdateStrategy extends AUpdateStrategy {

    /**
     * Constructor.
     */
    public ShadowUpdateStrategy() {
    }

    /**
     * Get shadow strategy name.
     * @return shadow strategy.
     */
    public String getType() {
        return "shadow";
    }

    /**
     * Update state.
     * @param self moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {
        // The general BFS chasing part
        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());
        Point tarUnitPos = AUpdateStrategy.getTarUnitPos(curUnitPos, others);
        Point nextStep =
            BFSHelper.nextChaseMove(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos);
        AUpdateStrategy.setNextDirection(self, curUnitPos, nextStep, allEnv);


    }
}
