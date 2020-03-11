package edu.rice.comp504.model.command;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Ghost;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.gameobj.Player;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;

/**
 * Update command.
 */
public class UpdateCmd implements IGameCmd {


    private AllEnv env;
    private MovingObj[] targets;

    /**
     * Update command constructor.
     *
     * @param env     game environment
     * @param targets target moving object
     */
    public UpdateCmd(AllEnv env, MovingObj... targets) {
        this.env = env;
        this.targets = targets;
    }

    /**
     * Moving object execute command.
     *
     * @param src moving object source
     */
    @Override
    public void execute(MovingObj src) {
        if (src instanceof Player) {
            src.getUpdateStrategy().updateState(src, env, targets);
        } else {
            if (!src.getUpdateStrategy().getType().equals("revive") && !src.getUpdateStrategy().getType().equals("retreat")) {
                boolean chase = false;
                for (MovingObj target : targets) {
                    if (BFSHelper.distanceManhattan(
                        Loc2LocHelper.toUnitLoc(src.getLocation()),
                        Loc2LocHelper.toUnitLoc(target.getLocation())
                    ) <= ((Ghost) src).getViewRange()) {
                        ((Ghost) src).updateStrategy("chase");
                        chase = true;
                    }
                }
                if (!chase) {
                    ((Ghost) src).updateStrategy("wander");
                }
            }
            src.getUpdateStrategy().updateState(src, env, targets);
            Point curUnitloc = Loc2LocHelper.toUnitLoc(src.getLocation());
            if (src.getUpdateStrategy().getType().equals("revive") && curUnitloc.x == env.getInitPlace().getGhostLoc()[0].x && curUnitloc.y == env.getInitPlace().getGhostLoc()[0].y) {
                src.setInvincible(true);
                ((Ghost) src).updateStrategy("wander");
                src.setSpeed(3);
            }
        }
    }
}
