package edu.rice.comp504.model.helper;

import java.awt.*;
import java.util.*;

/**
 * BFSHelper to make sure object's route to chase, wander, and retreat.
 */
public class BFSHelper {
    // pacman shortest move, possible move is non-wall unit, pick the closest distance.

    /**
     * Object's next chase move.
     *
     * @param walls walls
     * @param src   initial location
     * @param tar   target location
     * @return object next location
     */
    public static Point nextChaseMove(boolean[][] walls, Point src, Point tar) {
        // TODO: BFS
        Point res = new Point(src.x, src.y);
        int heuristic = 999999;
        for (Point move : getPossible(walls, src)) {
            int h = distanceBFS(walls, move, tar);
            if (h < heuristic) {
                heuristic = h;
                res = move;
            }
        }
        return res;
    }
    // will not go back, other route is random.
    // Not choosing the escaping steps, and get a random next step from the remaining steps.

    /**
     * Ghost next move in wander mode.
     *
     * @param walls walls
     * @param src   initial location
     * @param tar   target location
     * @return next location
     */
    public static Point nextRandomMove(boolean[][] walls, Point src, Point tar) {
        Point res = new Point(src.x, src.y);
        ArrayList<Point> moves = getPossible(walls, src);
        int heuristic = -1;
        int index = -1;
        for (int i = 0; i < moves.size(); i++) {
            int h = distanceBFS(walls, moves.get(i), tar);
            if (h > heuristic) {
                heuristic = h;
                index = i;
            }
        }
        moves.remove(index);
        Random rand = new Random();
        return moves.get(rand.nextInt(moves.size()));
    }

    /**
     * Get ghost's retreat route.
     *
     * @param walls walls
     * @param src   initial location
     * @param tar   target location
     * @return next location
     */
    public static Point nextEscapeMove(boolean[][] walls, Point src, Point tar) {
        // TODO: BFS
        Point res = new Point(src.x, src.y);
        int heuristic = -1;
        for (Point move : getPossible(walls, src)) {
            int h = distanceBFS(walls, move, tar);
            if (h > heuristic) {
                heuristic = h;
                res = move;
            }
        }
        return res;
    }

    /**
     * Get next possible position based on current position.
     *
     * @param walls walls
     * @param cur   current location
     * @return possible next position
     */
    private static ArrayList<Point> getPossible(boolean[][] walls, Point cur) {
        int cx = cur.x;
        int cy = cur.y;
        int width = walls.length;
        int height = walls[0].length;
        ArrayList<Point> moves = new ArrayList<Point>();
        if (cx > 0 && !walls[cx - 1][cy]) {
            moves.add(new Point(cx - 1, cy));
        }
        if (cx < width - 1 && !walls[cx + 1][cy]) {
            moves.add(new Point(cx + 1, cy));
        }
        if (cy > 0 && !walls[cx][cy - 1]) {
            moves.add(new Point(cx, cy - 1));
        }
        if (cy < height - 1 && !walls[cx][cy + 1]) {
            moves.add(new Point(cx, cy + 1));
        }
        return moves;
    }

    /**
     * Get distance.
     *
     * @param walls walls
     * @param src   src
     * @param tar   tar
     * @return BFS distance
     */
    public static int distanceBFS(boolean[][] walls, Point src, Point tar) {
        Set<Point> visited = new LinkedHashSet<>();
        Queue<Point> pointQueue = new LinkedList<>();
        pointQueue.add(src);
        visited.add(src);
        int dist = 0;
        while (pointQueue.size() > 0) {
            Queue<Point> temp = new LinkedList<>();
            for (Point cur : pointQueue) {
                if (cur.x == tar.x && cur.y == tar.y) {
                    return dist;
                }
                for (Point next : getPossible(walls, cur)) {
                    if (!visited.contains(next)) {
                        temp.add(next);
                        visited.add(next);
                    }
                }
            }
            pointQueue = temp;
            dist += 1;
        }
        return dist;
    }

    /**
     * Calculate Manhattan distance.
     *
     * @param src initial location
     * @param tar target location
     * @return Manhattan distance between src and tar
     */
    public static int distanceManhattan(Point src, Point tar) {
        return Math.abs(src.x - tar.x) + Math.abs(src.y - tar.y);
    }


}
