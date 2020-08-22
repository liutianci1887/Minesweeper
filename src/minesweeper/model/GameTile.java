package minesweeper.model;

public class GameTile {

    private int x;
    private int y;
    private int data;

    public GameTile(int xPos, int yPos, boolean isMine) {
        x = xPos;
        y = yPos;
        data = (isMine ? -1 : 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int newX) {
        x = newX;
    }

    public int getY() {
        return y;
    }

    public void setY(int newY) {
        y = newY;
    }

    public boolean isMine() {
        return data == -1;
    }

    public void setMine() {
        data = -1;
    }

    public int getSurroundingMines() {
        return data;
    }

    public void setSurroundingMines(int surroundingMines) {
        data = surroundingMines;
    }

}
