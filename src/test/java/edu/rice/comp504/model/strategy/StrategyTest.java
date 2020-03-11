package edu.rice.comp504.model.strategy;

import com.google.gson.Gson;
import edu.rice.comp504.model.DispatcherAdapter;
import edu.rice.comp504.model.gameobj.Player;
import edu.rice.comp504.model.helper.BFSHelper;
import edu.rice.comp504.model.helper.Loc2LocHelper;
import edu.rice.comp504.model.strategy.skill.AccelerateSkillStrategy;
import edu.rice.comp504.model.strategy.skill.ISkillStrategy;
import edu.rice.comp504.model.strategy.skill.InvincibleSkillStrategy;
import edu.rice.comp504.model.strategy.skill.TeleportSkillStrategy;
import edu.rice.comp504.model.strategy.update.*;
import junit.framework.TestCase;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

public class StrategyTest extends TestCase {
    private DispatcherAdapter dis;
    private Gson gson;

    public void setUp() throws Exception {
        super.setUp();
        dis = new DispatcherAdapter();
        gson = new Gson();
    }

    public void testUpdateStrategy() {
        AUpdateStrategy st;
        st = new BashfulUpdateStrategy();
        assertEquals("bashful", st.getType());
        st = new PokeyUpdateStrategy();
        assertEquals("pokey", st.getType());
        st = new RetreatUpdateStrategy();
        assertEquals("retreat", st.getType());
        st = new ReviveUpdateStrategy();
        assertEquals("revive", st.getType());
        st = new ShadowUpdateStrategy();
        assertEquals("shadow", st.getType());
        st = new WanderUpdateStrategy();
        assertEquals("wander", st.getType());
        st = new PacmanUpdateStrategy();
        assertEquals("pacman", st.getType());
    }

    public void testSkillStrategy() {
        ISkillStrategy st;
        st = new AccelerateSkillStrategy(0, 0, 0);
        assertEquals("accelerate", st.getName());
        st = new InvincibleSkillStrategy(0, 0, 0);
        assertEquals("invincible", st.getName());
        st = new TeleportSkillStrategy(0, 0, 0);
        assertEquals("teleport", st.getName());
    }

    public void testHelper() throws FileNotFoundException {
        dis.loadGame(0, 2, "public/map/map.json");
        Player player = (Player) dis.getPcs().getPropertyChangeListeners("player")[0];
        assertEquals(1, BFSHelper.distanceManhattan(new Point(0,0), new Point(0,1)));
        assertEquals(1, BFSHelper.distanceBFS(dis.getEnv().getImmutableEnv().getAllWalls(), new Point(14, 14), new Point(14,15)));
        assertNotNull(BFSHelper.nextChaseMove(dis.getEnv().getImmutableEnv().getAllWalls(), new Point(14, 14), new Point(14,15)));
        assertNotNull(BFSHelper.nextEscapeMove(dis.getEnv().getImmutableEnv().getAllWalls(), new Point(14, 14), new Point(14,15)));
        assertNotNull(BFSHelper.nextRandomMove(dis.getEnv().getImmutableEnv().getAllWalls(), new Point(14, 14), new Point(14,15)));
        assertNotNull(Loc2LocHelper.toUnitLoc(new Point(10,10)));
        assertNotNull(Loc2LocHelper.toCanvasLoc(new Point(10,10)));
        assertNotNull(Loc2LocHelper.canTurn(player, dis.getEnv().getImmutableEnv().getAllWalls()));
    }
}
