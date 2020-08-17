package minesweeper.model;

public class GameTile {

    private int x;
    private int y;
    private int data;

    public GameTile(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.data = (isMine ? -1 : 0);
    }

    public GameTile(int x, int y, int surroundingMines) {
        this.x = x;
        this.y = y;
        this.data = surroundingMines;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMine() {
        return data == -1;
    }

    public void setMine(boolean isMine) {
        this.data = -1;
    }

    public int getSurroundingMines() {
        return data;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.data = surroundingMines;
    }

}
