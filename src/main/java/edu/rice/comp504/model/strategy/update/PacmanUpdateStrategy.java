package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Ghost;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.gameobj.Player;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;
import edu.rice.comp504.model.strategy.skill.ISkillStrategy;

import java.util.List;
import java.awt.*;

/**
 * pacman update strategy.
 */
public class PacmanUpdateStrategy extends AUpdateStrategy {

    /**
     * Constructor.
     */
    public PacmanUpdateStrategy() {
    }

    /**
     * Get object type.
     *
     * @return object type.
     */
    public String getType() {
        return "pacman";
    }

    /**
     * Update state.
     *
     * @param self   moving object.
     * @param allEnv game environment.
     * @param others moving objects.
     */
    @Override
    public void updateState(MovingObj self, AllEnv allEnv, MovingObj... others) {
        Point curUnitPos = Loc2LocHelper.toUnitLoc(self.getLocation());

        // if there is a ghost.

        Point pos = self.getLocation();
        int speed = self.getSpeed();
        MovingObj.Direction d = self.getNewDirection();
        Point center = Loc2LocHelper.toCanvasLoc(Loc2LocHelper.toUnitLoc(pos));
        boolean isCenter = BFSHelper.distanceManhattan(center, pos) < speed;

        if (!isCenter) {
            self.update();
        } else {
            if (self.isWillTurn() && Loc2LocHelper.canTurn(self, allEnv.getImmutableEnv().getAllWalls())) {
                self.setDirection(self.getNewDirection());
                self.setWillTurn(false);
                self.setLocation(Loc2LocHelper.toCanvasLoc(curUnitPos));
            } else if (self.detectCollision(allEnv)) {
                self.setLocation(Loc2LocHelper.toCanvasLoc(curUnitPos));
            } else {
                self.update();
            }
        }
        Player player = (Player) self;
        player.eat(allEnv);

        for (MovingObj movingObj : others) {
            Ghost ghost = (Ghost) movingObj;
            if (player.innerCollision(movingObj)) {
                if (player.isInvincible() && ghost.isInvincible()) {
                    //do nothing.
                } else if (ghost.isInvincible() && !player.isInvincible()) {
                    player.caught(allEnv);
                } else {
                    if (ghost.getUpdateStrategyType().equals("revive")) {
                        //do nothing
                    } else {
                        player.addScore(200 * (int) Math.pow(2, allEnv.getGhostEatenNum()));
                        allEnv.setGhostEatenNum(allEnv.getGhostEatenNum() + 1);
                        ghost.updateStrategy("revive");
                    }
                }
            }
        }

        for (ISkillStrategy skill : player.getSkillStrategy()) {
            skill.update(player);
        }
    }

    /**
     * Update state Keyboard.
     *
     * @param self   moving object.
     * @param signal input.
     * @param env    game environment.
     */
    public void updateStateKeyboard(MovingObj self, String signal, AllEnv env) {
        MovingObj.Direction d;
        switch (signal) {
            case "LEFT":
                d = MovingObj.Direction.LEFT;
                break;
            case "RIGHT":
                d = MovingObj.Direction.RIGHT;
                break;
            case "UP":
                d = MovingObj.Direction.UP;
                break;
            case "DOWN":
                d = MovingObj.Direction.DOWN;
                break;
            default:
                d = null;
                assert self instanceof Player;
                List<ISkillStrategy> strategyList = ((Player) self).getSkillStrategy();
                for (ISkillStrategy skill : strategyList) {
                    if (skill.getName().equals(signal)) {
                        skill.cast((Player) self, env);
                        break;
                    }
                }
                break;
        }
        if (d != null) {
            self.setWillTurn(true);
            self.setNewDirection(d);
        }
    }
}
