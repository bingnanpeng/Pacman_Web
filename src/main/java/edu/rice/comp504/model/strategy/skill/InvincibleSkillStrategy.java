package edu.rice.comp504.model.strategy.skill;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Player;

/**
 * Invincible SkillStrategy.
 */
public class InvincibleSkillStrategy extends ISkillStrategy {
    /**
     * Invincible Skill Strategy.
     *
     * @param maxCd      max cooling down time.
     * @param maxLasting max lasting time.
     * @param cost       cost.
     */
    public InvincibleSkillStrategy(int maxCd, int maxLasting, int cost) {
        super(maxCd, maxLasting, cost);
    }

    /**
     * Skill cast.
     *
     * @param player player.
     * @param allEnv game environment.
     */
    @Override
    public void cast(Player player, AllEnv allEnv) {
        if (this.getCd() == 0) {
            player.setInvincible(true);
            this.setCd(this.getMaxCd());
            this.setLasting(this.getMaxLasting());
        }
    }


    /**
     * Skill cancel.
     *
     * @param caster player who uses skill.
     */
    @Override
    public void cancel(Player caster) {
        caster.setInvincible(false);
    }
}
