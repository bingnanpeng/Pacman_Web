package edu.rice.comp504.model.helper;

import edu.rice.comp504.model.gameobj.MovingObj;

import java.awt.*;

public class Loc2LocHelper {
    private static int UNIT_WIDTH = 20;
    private static int UNIT_HEIGHT = 20;
    private static int BORDER = 10;

    /**
     * This canvas loc is the centre of that grid.
     *
     * @param point unit location
     * @return canvas location
     */
    public static Point toCanvasLoc(Point point) {
        return new Point(point.x * UNIT_WIDTH + UNIT_WIDTH / 2 + BORDER,
            point.y * UNIT_HEIGHT + UNIT_HEIGHT / 2 + BORDER);
    }

    /**
     * This canvas loc can be any point in that corresponding grid.
     *
     * @param point canvas location
     * @return unit location
     */
    public static Point toUnitLoc(Point point) {
        return new Point((point.x - BORDER) / UNIT_WIDTH,
            (point.y - BORDER) / UNIT_HEIGHT);
    }

    /**
     * Decide whether the obj can turn or not.
     *
     * @param obj   pacman or ghost
     * @param walls the grid
     * @return unit location
     */

    public static boolean canTurn(MovingObj obj, boolean[][] walls) {
        Point pos = obj.getLocation();
        int speed = obj.getSpeed();
        MovingObj.Direction d = obj.getNewDirection();
        Point center = toCanvasLoc(toUnitLoc(pos));
        boolean isCenter = BFSHelper.distanceManhattan(center, pos) < speed;
        boolean canTurn = directionNoWall(pos, d, walls);
        return isCenter && canTurn;
    }

    /**
     * Detect wall.
     *
     * @param pos   pos
     * @param d     direction
     * @param walls walls
     * @return wall existence
     */
    private static boolean directionNoWall(Point pos, MovingObj.Direction d, boolean[][] walls) {

        int unitX = toUnitLoc(pos).x;
        int unitY = toUnitLoc(pos).y;
        int row = walls.length;
        int col = walls[0].length;

        if (d == MovingObj.Direction.LEFT) {
            unitX = (unitX - 1 + row) % row;
        } else if (d == MovingObj.Direction.RIGHT) {
            unitX = (unitX + 1) % row;
        } else if (d == MovingObj.Direction.DOWN) {
            unitY = (unitY + 1) % col;
        } else {
            unitY = (unitY - 1 + col) % col;
        }
        return !walls[unitX][unitY];
    }
}
