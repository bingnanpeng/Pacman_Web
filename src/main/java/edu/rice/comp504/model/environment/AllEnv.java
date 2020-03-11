package edu.rice.comp504.model.environment;

import edu.rice.comp504.model.gameobj.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * All Game environment.
 */
public class AllEnv {
    private boolean[][] emptyPlaces;
    private EdibleObj[][] allEdibles;
    private final ImmutableEnv immutableEnv;
    private InitPlace initPlace;
    private int frightenedTimer = 0;
    private int maxFrightenedTimer = 100;
    private int fruitTimer = 0;
    private int maxFruitTimer = 100;
    private int dotNums = 0;
    private int ghostEatenNum; //Record double score when eating ghost.
    private int level;
    private boolean gameover;

    /**
     * Constructor.
     *
     * @param levelMap level map
     * @throws IllegalArgumentException wrong number denoted on level map
     */
    public AllEnv(int[][] levelMap, int level) throws IllegalArgumentException {
        gameover = false;
        this.level = level;
        int row = levelMap.length;
        int col = levelMap[0].length;

        EdibleObj[][] edibleObjs = new EdibleObj[row][col];
        for (EdibleObj[] edibleObj : edibleObjs) {
            Arrays.fill(edibleObj, NullObj.getInstance());
        }

        boolean[][] emptyPlaces = new boolean[row][col];
        boolean[][] walls = new boolean[row][col];
        boolean[][] gates = new boolean[row][col];
        boolean[][] ghostAreas = new boolean[row][col];
        List<Point> ghostLoc = new ArrayList<>();
        List<Point> playerLoc = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                switch (levelMap[i][j]) {
                    case 0: // empty way
                        emptyPlaces[i][j] = true;
                        break;
                    case 1: // dot
                        edibleObjs[i][j] = new Dot();
                        dotNums += 1;
                        break;
                    case 2: // gate
                        gates[i][j] = true;
                        break;
                    case 3: // energizer
                        edibleObjs[i][j] = new Energizer();
                        break;
                    case 4: // ghost init place
                        ghostLoc.add(new Point(i, j));
                        break;
                    case 5: // player init place
                        playerLoc.add(new Point(i, j));
                        break;
                    case 6:
                        ghostAreas[i][j] = true;
                        break;
                    case 7: // TODO Player #2 init place
                        playerLoc.add(new Point(i, j));
                        break;

                    case 9: // wall
                        walls[i][j] = true;
                        break;
                    default:
                }
            }
        }

        this.emptyPlaces = emptyPlaces;
        allEdibles = edibleObjs;
        immutableEnv = new ImmutableEnv(walls, gates, ghostAreas);
        initPlace = new InitPlace(playerLoc.toArray(Point[]::new), ghostLoc.toArray(Point[]::new));
    }

    /**
     * updateMode.
     */
    public void updateMode() {
        if (frightenedTimer > 0) {
            frightenedTimer--;
        }
        if (fruitTimer < maxFruitTimer) {
            fruitTimer++;
        }
    }

    /**
     * getFrightenedTimer.
     *
     * @return frightenedTimer.
     */
    public int getFrightenedTimer() {
        return frightenedTimer;
    }


    /**
     * setFrightenedTimer.
     */
    public void setFrightenedTimer(int frightenedTimer) {
        this.frightenedTimer = frightenedTimer;
    }

    public int getMaxFrightenedTimer() {
        return maxFrightenedTimer;
    }

    public void setFruitTimer(int fruitTimer) {
        this.fruitTimer = fruitTimer;
    }

    public int getFruitTimer() {
        return fruitTimer;
    }

    public int getMaxFruitTimer() {
        return maxFruitTimer;
    }

    public int getDotNums() {
        return dotNums;
    }

    public void setDotNums(int dotNums) {
        this.dotNums = dotNums;
    }

    public int getGhostEatenNum() {
        return ghostEatenNum;
    }

    public void setGhostEatenNum(int ghostEatenNum) {
        this.ghostEatenNum = ghostEatenNum;
    }

    /**
     * getFruit.
     */
    public void randFruit() {
        Fruit fruit = Fruit.getInstance();
        Point point = randEmptyPlace();
        allEdibles[point.x][point.y] = fruit;
        emptyPlaces[point.x][point.y] = false;
    }

    /**
     * randEmptyPlace.
     *
     * @return randEmptyPlace.
     */
    public Point randEmptyPlace() {
        int row = emptyPlaces.length;
        int col = emptyPlaces[0].length;

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (emptyPlaces[i][j]) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points.get((int) (Math.random() * points.size()));
    }

    /**
     * setEmptyPlace.
     *
     * @param point setEmptyPlace.
     */
    public void setEmptyPlace(Point point) {
        emptyPlaces[point.x][point.y] = true;
    }

    public void setNonEmptyPlace(Point point) {
        emptyPlaces[point.x][point.y] = false;
    }

    public void setEdiblePlace(Point point, EdibleObj edibleObj) {
        allEdibles[point.x][point.y] = edibleObj;
    }


    /**
     * getAllEdibles.
     *
     * @return getAllEdibles.
     */
    public EdibleObj[][] getAllEdibles() {
        return allEdibles;
    }

    /**
     * getImmutableEnv.
     *
     * @return getImmutableEnv.
     */
    public ImmutableEnv getImmutableEnv() {
        return immutableEnv;
    }

    /**
     * getInitPlace.
     *
     * @return getInitPlace.
     */
    public InitPlace getInitPlace() {
        return initPlace;
    }

    /**
    * getStaticMatrix.
     *
     * @return getStaticMatrix
    * */
    public int[][] getStaticMatrix() {
        int row = emptyPlaces.length;
        int col = emptyPlaces[0].length;
        int[][] staticMatrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (emptyPlaces[i][j]) {
                    staticMatrix[i][j] = 0;
                } else if (getAllEdibles()[i][j].getClass().getSimpleName().equals("Dot")) {
                    staticMatrix[i][j] = 1;
                } else if (immutableEnv.allGates[i][j]) {
                    staticMatrix[i][j] = 2;
                } else if (getAllEdibles()[i][j].getClass().getSimpleName().equals("Energizer")) {
                    staticMatrix[i][j] = 3;
                } else if (i == initPlace.playerLoc[0].x && j == initPlace.playerLoc[0].y) {
                    staticMatrix[i][j] = 5;
                } else if (initPlace.playerLoc.length == 2 && i == initPlace.playerLoc[1].x && j == initPlace.playerLoc[1].y) {
                    staticMatrix[i][j] = 7;
                } else if (immutableEnv.ghostAreas[i][j]) {
                    staticMatrix[i][j] = 6;
                } else if (getAllEdibles()[i][j].getClass().getSimpleName().equals("Fruit")) {
                    staticMatrix[i][j] = 8;
                } else if (immutableEnv.allWalls[i][j]) {
                    staticMatrix[i][j] = 9;
                } else {
                    staticMatrix[i][j] = 4;
                }
            }
        }
        return staticMatrix;
    }

    public int getLevel() {
        return level;
    }

    /**
     * ImmutableEnv.
     */
    public static class ImmutableEnv {
        private final boolean[][] allWalls;
        private final boolean[][] allGates;
        private final boolean[][] ghostAreas;

        /**
         * ImmutableEnv.
         *
         * @param allWalls   ImmutableEnv.
         * @param allGates   ImmutableEnv.
         * @param ghostAreas ImmutableEnv.
         */
        public ImmutableEnv(boolean[][] allWalls, boolean[][] allGates, boolean[][] ghostAreas) {
            this.allWalls = allWalls;
            this.allGates = allGates;
            this.ghostAreas = ghostAreas;
        }

        /**
         * getAllWalls.
         *
         * @return getAllWalls.
         */
        public boolean[][] getAllWalls() {
            return allWalls;
        }

        /**
         * getAllGates.
         *
         * @return getAllGates.
         */
        public boolean[][] getAllGates() {
            return allGates;
        }

        /**
         * getGhostAreas.
         *
         * @return getGhostAreas.
         */
        public boolean[][] getGhostAreas() {
            return ghostAreas;
        }
    }

    public boolean isGameover() {
        return gameover;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    /**
     * InitPlace.
     */
    public static class InitPlace {
        private final Point[] playerLoc;
        private final Point[] ghostLoc;

        /**
         * InitPlace.
         *
         * @param playerLoc InitPlace.
         * @param ghostLoc  InitPlace.
         */
        public InitPlace(Point[] playerLoc, Point[] ghostLoc) {
            this.playerLoc = playerLoc;
            this.ghostLoc = ghostLoc;
        }

        /**
         * getPlayerLoc.
         *
         * @return getPlayerLoc.
         */
        public Point[] getPlayerLoc() {
            return playerLoc;
        }

        /**
         * getPlayerLoc.
         *
         * @return getPlayerLoc.
         */
        public Point[] getGhostLoc() {
            return ghostLoc;
        }
    }
}
