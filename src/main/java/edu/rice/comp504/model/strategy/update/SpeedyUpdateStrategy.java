package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Ghost;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * It runs the fast, and it turns differently at the corner.
 * According to wikipedia:
 * It's programmed to take a route to a position four spaces ahead of wherever Pac-Man’s moving,
 * which can easily leave the player trapped.
 */
public class SpeedyUpdateStrategy extends AUpdateStrategy {

    /**
     * Constructor.
     */
    public SpeedyUpdateStrategy() {
    }

    /**
     * Get speedy strategy name.
     *
     * @return speedy strategy name.
     */
    public String getType() {
        return "speedy";
    }

    /**
     * Update State.
     * 
     * @param self moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {

        // First special thing of Speedy/Pinky ghost
        //((Ghost)self).setSpeed(10); // Hardcore a high speed

        // The general BFS chasing part
        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());
        Point tarUnitPos = AUpdateStrategy.getTarUnitPos(curUnitPos, others);
        Point nextStep;
        boolean[][] walls = allEnv.getImmutableEnv().getAllWalls();

        // Second, the ghost will target the predict position of pacman
        if (BFSHelper.distanceBFS(allEnv.getImmutableEnv().getAllWalls(), curUnitPos, tarUnitPos) < 5) {
            nextStep = BFSHelper.nextChaseMove(walls, curUnitPos, tarUnitPos);
        } else {
            int i = 0;
            int tx = tarUnitPos.x;
            int ty = tarUnitPos.y;
            while (self.getDirection() == MovingObj.Direction.LEFT && !walls[tx - 1][ty] && i < 5) {
                i++;
                tx--;
            }
            while (self.getDirection() == MovingObj.Direction.RIGHT && !walls[tx + 1][ty] && i < 5) {
                i++;
                tx++;
            }
            while (self.getDirection() == MovingObj.Direction.DOWN && !walls[tx][ty + 1] && i < 5) {
                i++;
                ty++;
            }
            while (self.getDirection() == MovingObj.Direction.UP && !walls[tx][ty - 1] && i < 5) {
                i++;
                ty--;
            }
            nextStep = BFSHelper.nextChaseMove(walls, curUnitPos, new Point(tx, ty));
        }
        AUpdateStrategy.setNextDirection(self, curUnitPos, nextStep, allEnv);

    }
}
