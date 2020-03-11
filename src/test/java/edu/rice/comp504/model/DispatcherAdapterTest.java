package edu.rice.comp504.model;

import com.google.gson.Gson;
import edu.rice.comp504.model.DispatcherAdapter.GameBean;
import edu.rice.comp504.model.gameobj.Energizer;
import edu.rice.comp504.model.gameobj.Player;
import junit.framework.TestCase;

import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.Random;

public class DispatcherAdapterTest extends TestCase {
    private DispatcherAdapter dis;
    private Gson gson;

    public void setUp() throws Exception {
        super.setUp();
        dis = new DispatcherAdapter();
        gson = new Gson();
    }

    public void tearDown() throws Exception {
    }


    public void testLoadGame() throws FileNotFoundException {

        GameBean gameBean;
        gameBean = dis.loadGame(0, 2, "public/map/map.json");
        System.out.println(gson.toJson(gameBean));

    }

    public void testGetPlayers() throws FileNotFoundException {
        GameBean bean1 = dis.loadGame(0, 2, "public/map/map.json");
        GameBean bean2 = dis.update();
    }



    public void testReset() throws FileNotFoundException {

        dis.loadGame(0, 2, "public/map/map.json");
        int num1 = dis.getPcs().getPropertyChangeListeners().length;
        dis.reset();
        int num2 = dis.getPcs().getPropertyChangeListeners().length;
        assertTrue("test reset", num1 != num2);

    }

    public void testEatEnergizer() throws FileNotFoundException {
        Energizer energizer = new Energizer();
        dis.loadGame(0, 2, "public/map/map.json");
        PropertyChangeListener[] listeners = dis.getPcs().getPropertyChangeListeners("player");
        for (int i = 0; i < listeners.length; i++) {

            Player player = (Player) listeners[i];
            int oldScore = player.getScore();
            energizer.setEaten(player, dis.getEnv());
            int newScore = player.getScore();
            assertTrue("test eat energinzer", oldScore != newScore);
        }

    }

    public void testUpdate() throws FileNotFoundException {

        GameBean bean = dis.loadGame(0, 2, "public/map/map.json");
        assertTrue("test update", bean != null);
        String[] input = new String[]{"UP", "DOWN", "LEFT", "RIGHT", "teleport", "accelerate", "invincible"};
        for (int i = 0; i < 500; i++) {
            dis.update(0, input[new Random().nextInt(7)]);
            dis.update();
            dis.update();
            dis.update();
            dis.update();
            dis.update();
        }
        GameBean bean2 = dis.update();
        dis.getEnv().randFruit();
        assertTrue("test update", bean2 != null);

        String gson1 = gson.toJson(bean);
        String gson2 = gson.toJson(bean2);
        assertTrue("test update, two strings are different", !gson1.equals(gson2));

    }
}