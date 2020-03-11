package edu.rice.comp504.model.strategy.skill;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Player;

/**
 * AccelerateSkillStrategy.
 */
public class AccelerateSkillStrategy extends ISkillStrategy {
    /**
     * Constructor.
     *
     * @param maxCd      cool down time.
     * @param maxLasting max lasting time.
     * @param cost       cost.
     */
    public AccelerateSkillStrategy(int maxCd, int maxLasting, int cost) {
        super(maxCd, maxLasting, cost);
    }

    /**
     * Skill cast.
     *
     * @param player player in game.
     * @param allEnv game environment.
     */
    @Override
    public void cast(Player player, AllEnv allEnv) {
        if (this.getCd() == 0) {
            player.setSpeed(player.getSpeed() * 2);
            this.setCd(this.getMaxCd());
            this.setLasting(this.getMaxLasting());
        }
    }

    /**
     * Cancel skill.
     *
     * @param caster caster.
     */
    @Override
    public void cancel(Player caster) {
        caster.setSpeed(caster.getSpeed() / 2);
    }
}
