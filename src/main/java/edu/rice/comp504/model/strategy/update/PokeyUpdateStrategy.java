package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Ghost;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * It is short-sighted.
 */
public class PokeyUpdateStrategy extends AUpdateStrategy {

    /**
     * Constructor.
     */
    public PokeyUpdateStrategy() {
    }

    /**
     * Get pacman type name.
     *
     * @return pacman type name
     */
    public String getType() {
        return "pokey";
    }

    // self is the ghost, others are pacman

    /**
     * Update state.
     *
     * @param self moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {

        // The only part specific to pokey
        ((Ghost) self).setViewRange(5);


        // The general BFS chasing part
        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());
        Point tarUnitPos = AUpdateStrategy.getTarUnitPos(curUnitPos, others);
        Point nextStep =
            BFSHelper.nextChaseMove(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos);
        AUpdateStrategy.setNextDirection(self, curUnitPos, nextStep, allEnv);

    }
}
