package minesweeper.model;

public class GameBoard {

    private final GameTile[][] tiles;
    private final int dimensionX;
    private final int dimensionY;

    public GameBoard(int dX, int dY) {
        dimensionX = dX;
        dimensionY = dY;

        // Generate blank board
        tiles = new GameTile[dimensionX][dimensionY];
    }

    public GameTile getTile(int x, int y) {
        if (x < 0 || x > dimensionX - 1 || y < 0 || y > dimensionY - 1) {
            return null;
        }
        return tiles[x][y];
    }

    public void setTile(int x, int y, GameTile tile) {
        tiles[x][y] = tile;
    }

    public boolean isTileFlagged(int x, int y) {
        if (x < 0 || x > dimensionX - 1 || y < 0 || y > dimensionY - 1) {
            return false;
        }

        return getTile(x, y).isFlagged();
    }

    public GameTile[][] getTiles() {
        return tiles;
    }

    public void incrementMineCount(int x, int y) {
        GameTile tile = getTile(x, y);
        if (tile == null || tile.isMine()) {
            return;
        }

        // Increment a non-mine tile's counter by 1.
        tile.setSurroundingMines(tile.getSurroundingMines() + 1);
    }

    // For debug
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