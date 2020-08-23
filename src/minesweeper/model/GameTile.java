package minesweeper.model;

public class GameTile {

    private int x;
    private int y;
    private int data; // -1: a mine, [0, 8]: number of surrounding mines
    private int viewStatus; // 0: hidden, 1: flagged, 2: visible

    public GameTile(int xPos, int yPos, boolean isMine) {
        x = xPos;
        y = yPos;
        data = (isMine ? -1 : 0);
        viewStatus = 0;
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

    public boolean isHidden() {
        return viewStatus == 0;
    }

    public void setFlag() {
        viewStatus = 1;
    }

    public boolean isFlagged() {
        return viewStatus == 1;
    }

    public void setVisible() {
        viewStatus = 2;
    }
}
