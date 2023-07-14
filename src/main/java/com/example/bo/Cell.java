package com.example.bo;

public class Cell {
    private final int x,y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell cell){
            return cell.x == this.x && cell.y == this.y;
        }
        return false;
    }
}
