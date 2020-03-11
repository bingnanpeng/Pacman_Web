package edu.rice.comp504.model.strategy.skill;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Player;
import edu.rice.comp504.model.helper.Loc2LocHelper;

import java.awt.*;
import java.util.ArrayList;

/**
 * TeleportSkillStrategy.
 */
public class TeleportSkillStrategy extends ISkillStrategy {
    /**
     * TeleportSkillStrategy.
     *
     * @param maxCd      max cooling down time.
     * @param maxLasting max lasting time.
     * @param cost       skill cost.
     */
    public TeleportSkillStrategy(int maxCd, int maxLasting, int cost) {
        super(maxCd, maxLasting, cost);
    }

    /**
     * Skill cast.
     *
     * @param player game player.
     * @param allEnv game environment.
     */
    @Override
    public void cast(Player player, AllEnv allEnv) {
        if (this.getCd() == 0) {
            player.setLocation(Loc2LocHelper.toCanvasLoc(allEnv.randEmptyPlace()));
            this.setCd(this.getMaxCd());
            this.setLasting(this.getMaxLasting());
        }
    }

    /**
     * Skill cancel.
     *
     * @param caster player
     */
    @Override
    public void cancel(Player caster) {

    }

}
