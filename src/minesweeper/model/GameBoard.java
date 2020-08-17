package minesweeper.model;

public class GameBoard {

    private GameTile[][] tiles;
    private final int dimensionX;
    private final int dimensionY;

    public GameBoard(int dimensionX, int dimensionY) {
        this.tiles = new GameTile[dimensionX][dimensionY];
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }

    public GameTile getTile(int x, int y) {
        if (x < 0 || x > dimensionX - 1 || y < 0 || y > dimensionY - 1) {
            return null;
        }
        return tiles[x][y];
    }

    public void setTile(int x, int y, GameTile tile) {
        this.tiles[x][y] = tile;
    }

    public GameTile[][] getTiles() {
        return tiles;
    }

    public void setTiles(GameTile[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (GameTile[] row : tiles) {
            result.append("|");
            for (GameTile tile : row) {
                if (tile != null && tile.isMine()) {
                    result.append("M|");
                } else if (tile != null) {
                    result.append(tile.getSurroundingMines()).append("|");
                } else {
                    result.append("O|");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
}