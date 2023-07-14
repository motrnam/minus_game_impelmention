package com.example.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// 0 not bomb not detected
// 1 bomb not detected
// 2 not bomb detected
// 3 bomb detected
public class Game {
    private final Levels levels;
    private final int[][] map;
    private final int[][] numberMap;
    private ArrayList<Cell> cellArrayList = new ArrayList<>();

    public int getRemindedBomb() {
        return remindedBomb;
    }

    private int remindedBomb;

    public Game(Levels levels) {
        this.levels = levels;
        map = new int[levels.getHeight()][levels.getWidth()];
        numberMap = new int[levels.getHeight()][levels.getWidth()];
        initMap();
        initNumberMap();
        remindedBomb = levels.getNumberOfBomb();
    }

    public Levels getLevels() {
        return levels;
    }

    private void initNumberMap() {
        int temp = 0;
        for (int i = 0; i < levels.getHeight(); i++)
            for (int j = 0; j < levels.getWidth(); j++) {
                temp = 0;
                ArrayList<Cell> cells = getNeighbour1(i, j);
                for (Cell myCell : cells) {
                    if (map[myCell.getX()][myCell.getY()] == 1)
                        temp++;
                }

                numberMap[i][j] = temp;
            }
    }

    private void initMap() {
        for (int[] ints : map) Arrays.fill(ints, 0);
        Random random = new Random();
        ArrayList<Integer> indexArraylist = new ArrayList<>();
        for (int i = 0; i < levels.getNumberOfBomb(); i++) {
            int index = random.nextInt(0, levels.getHeight() * levels.getWidth() - i);
            for (Integer integer : indexArraylist) {
                if (integer <= index)
                    index++;
            }
            map[index % levels.getHeight()][index / levels.getHeight()] = 1;
            indexArraylist.add(index);
        }
    }

    public boolean checkBomb(int x, int y) {
        return map[x][y] % 2 == 1;
    }

    public boolean checkWin() {
        for (int[] h : map) {
            for (int w : h)
                if (w == 0) return false;
        }
        return true;
    }

    public void addBomb(int x, int y) {
        if (remindedBomb > 0)
            remindedBomb--;
    }

    public void removeBomb(int x, int y) {
        if (remindedBomb < levels.getNumberOfBomb())
            remindedBomb++;
    }

    private ArrayList<Cell> getNeighbour(int x, int y) {
        ArrayList<Cell> result = new ArrayList<>();
        if (x != 0 && x != levels.getHeight() - 1) {
            result.add(new Cell(x - 1, y));
            result.add(new Cell(x + 1, y));
        } else if (x == 0) {
            result.add(new Cell(1, y));
        } else if (x == levels.getHeight() - 1) {
            result.add(new Cell(x - 1, y));
        }


        if (y != 0 && y != levels.getWidth() - 1) {
            result.add(new Cell(x, y - 1));
            result.add(new Cell(x, y + 1));
        } else if (y == 0) {
            result.add(new Cell(x, 1));
        } else if (y == levels.getWidth() - 1) {
            result.add(new Cell(x, y - 1));
        }
        // result is my Array
        return result;
    }

    private boolean checkBounds(int x, int y) {
        return (x >= 0 && x < levels.getHeight() && y >= 0 && y < levels.getWidth());
    }

    private ArrayList<Cell> getNeighbour1(int x, int y) {
        ArrayList<Cell> result = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (checkBounds(x + i, y + j) && (i != 0 || j != 0)) {
                    result.add(new Cell(x + i, y + j));
                }
            }
        }
        return result;
    }

    public void setOn(int x, int y) {
        cellArrayList = new ArrayList<>();
        cellArrayList.add(new Cell(x, y));
    }

    public void notBomb(int x, int y) {
        if (map[x][y] == 2 || map[x][y] == 1)
            return;
        map[x][y] = 2;
        cellArrayList.add(new Cell(x, y));
        if (numberMap[x][y] != 0)
            return;
        for (int i = 0;i< cellArrayList.size();i++) {
            if (numberMap[x][y] != 1){
                ArrayList<Cell> nei = getNeighbour1(x,y);
                for (int j = 0; j < nei.size(); j++) {
                    notBomb(nei.get(j).getX(),nei.get(j).getY());
                }
            }
        }
    }

    public ArrayList<Cell> getCellArrayList() {
        return cellArrayList;
    }

    public int getNumber(int x, int y) {
        return numberMap[x][y];
    }
}
