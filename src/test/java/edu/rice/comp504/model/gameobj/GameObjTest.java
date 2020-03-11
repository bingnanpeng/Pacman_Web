package edu.rice.comp504.model.gameobj;

import edu.rice.comp504.model.strategy.update.*;
import junit.framework.TestCase;

import java.awt.*;

public class GameObjTest extends TestCase {

    public void testGetType() {
        Player player = new Player(0, new Point(), new PacmanUpdateStrategy(), 0, 0, null);
        player.update();
        assertEquals("player object: type = player", "player", player.getType());

    }

    public void testEdibleObj(){
        EdibleObj tempDot = new Dot();
        assertEquals("test dot score.",10, tempDot.getScore());
        assertEquals("test dot score.","dot", tempDot.getType());

        EdibleObj tempEnergizer = new Energizer();
        assertEquals("test energizer score.",50, tempEnergizer.getScore());
        assertEquals("test dot score.","energizer", tempEnergizer.getType());

        EdibleObj tempFruit = Fruit.getInstance();
        assertEquals("test dot score.",100, tempFruit.getScore());
        assertEquals("test dot score.","fruit", tempFruit.getType());

        Player player = new Player(0, new Point(), null, 0, 0, null);
        assertEquals("player object: type = player", "player", player.getType());

    }

    public void testGetterAndSetter() {
        Player player = new Player(0, new Point(), null, 0, 0, null);
        player.setColor("Black");
        assertEquals("test set color", "Black", player.getColor());
        player.setSize(1);
        assertEquals("test set color", 1, player.getSize().intValue());
        player.setSrc("src");
        assertEquals("test src", "src", player.getSrc());



    }
    public void testMovingObj(){
        Player player = new Player(0, new Point(), null, 0, 0, null);

        Ghost ghost1 = new Ghost(1, new Point(), new RetreatUpdateStrategy(), new WanderUpdateStrategy(), new PokeyUpdateStrategy(), new ReviveUpdateStrategy());
        Ghost ghost2 = new Ghost(1, new Point(), new RetreatUpdateStrategy(), new WanderUpdateStrategy(), new SpeedyUpdateStrategy(), new ReviveUpdateStrategy());
        Ghost ghost3 = new Ghost(1, new Point(), new RetreatUpdateStrategy(), new WanderUpdateStrategy(), new BashfulUpdateStrategy(), new ReviveUpdateStrategy());
        Ghost ghost4 = new Ghost(1, new Point(), new RetreatUpdateStrategy(), new WanderUpdateStrategy(), new ShadowUpdateStrategy(), new ReviveUpdateStrategy());
        ghost1.updateStrategy("revive");
        ghost1.update();
        ghost2.update();
        ghost3.update();
        ghost4.update();

        assertEquals("player object: type = player", "player", player.getType());

    }
}