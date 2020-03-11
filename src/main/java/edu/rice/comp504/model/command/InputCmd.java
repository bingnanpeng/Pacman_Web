package edu.rice.comp504.model.command;

import edu.rice.comp504.model.environment.AllEnv;
import edu.rice.comp504.model.gameobj.MovingObj;
import edu.rice.comp504.model.gameobj.Player;
import edu.rice.comp504.model.strategy.skill.ISkillStrategy;
import edu.rice.comp504.model.strategy.update.AUpdateStrategy;
import edu.rice.comp504.model.strategy.update.PacmanUpdateStrategy;

/**
 * Input command.
 */
public class InputCmd implements IGameCmd {

    private String input;
    private int id; // 用户ID
    private AllEnv env;

    /**
     * Input command constructor.
     *
     * @param id    player id
     * @param input player input
     * @param env   environment
     */
    public InputCmd(int id, String input, AllEnv env) {
        this.input = input;
        this.id = id;
        this.env = env;
    }

    /**
     * Execute moving object.
     *
     * @param src moving object image source
     */
    @Override
    public void execute(MovingObj src) {
        assert src instanceof Player;
        if (((Player) src).getId() == id) {
            AUpdateStrategy strategy = src.getUpdateStrategy();
            assert strategy instanceof PacmanUpdateStrategy;
            ((PacmanUpdateStrategy) strategy).updateStateKeyboard(src, input, env);
        }
    }
}
