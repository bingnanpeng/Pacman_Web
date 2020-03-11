package edu.rice.comp504.model.strategy.skill;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.Player;

/**
 * SkillStrategy Interface.
 */
public abstract class ISkillStrategy {
    private String name;
    private int cd = 0;
    private int lasting = 0;
    private final int maxCd;
    private final int maxLasting;
    private final int cost;

    {
        String classname = this.getClass().getSimpleName();
        name = classname.substring(0, classname.indexOf("SkillStrategy")).toLowerCase();
    }

    /**
     * Constructor.
     *
     * @param maxCd      max cooling down time.
     * @param maxLasting max lasting time.
     * @param cost       cost.
     */
    public ISkillStrategy(int maxCd, int maxLasting, int cost) {
        this.maxCd = maxCd;
        this.maxLasting = maxLasting;
        this.cost = cost;
    }

    /**
     * Skill cast.
     *
     * @param player player in game.
     * @param allEnv game environment.
     */
    public abstract void cast(Player player, AllEnv allEnv);

    public abstract void cancel(Player caster);

    /**
     * update it in each period to calculate the lasting and cd information.
     */
    public void update(Player self) {
        if (lasting > 0) {
            lasting--;
            if (lasting == 0) {
                this.cancel(self);
            }
        } else if (cd > 0) {
            cd--;
        }
    }

    /**
     * Set cooling down time.
     *
     * @param cd cooling down time.
     */
    public void setCd(int cd) {
        this.cd = cd;
    }


    /**
     * Set skill lasting time.
     *
     * @param lasting lasting time.
     */
    public void setLasting(int lasting) {
        this.lasting = lasting;
    }


    /**
     * Get skill cooling down time.
     *
     * @return cooling down time.
     */
    public int getCd() {
        return cd;
    }

    /**
     * Get skill lasting time.
     *
     * @return skill lasting time.
     */
    public int getLasting() {
        return lasting;
    }

    /**
     * Get max cooling down time.
     *
     * @return cooling down time.
     */
    public int getMaxCd() {
        return maxCd;
    }

    /**
     * Get max lasting time.
     *
     * @return max lasting time.
     */
    public int getMaxLasting() {
        return maxLasting;
    }

    /**
     * Get skill cost.
     *
     * @return skill cost.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Get name.
     *
     * @return skill name.
     */
    public String getName() {
        return this.name;
    }
}
