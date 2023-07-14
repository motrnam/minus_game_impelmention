package com.example.bo;

public enum Levels {
    EASY(10,10,10),
    MEDIUM(15,15,15),
    HARD(20,20,20);
    private final int width;
    private final int height;
    private final int numberOfBomb;

    Levels(int width, int height, int numberOfBomb) {
        this.width = width;
        this.height = height;
        this.numberOfBomb = numberOfBomb;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumberOfBomb() {
        return numberOfBomb;
    }
}
