package minesweeper.controller;

import minesweeper.application.MinesweeperApplication;
import minesweeper.model.GameBoard;
import minesweeper.model.GameTile;
import minesweeper.view.ViewController;

import java.util.ArrayList;
import java.util.Random;

public class GameController {

    public static void newBoard(int dimensionX, int dimensionY, int mineCount) {
        GameBoard gameBoard = new GameBoard(dimensionX, dimensionY);
        MinesweeperApplication.setGameBoard(gameBoard);

        // First generate the mines
        Random rng = new Random();
        ArrayList<int[]> mines = new ArrayList<>();
        for (int i = 0; i < mineCount; i++) {
            int x, y;

            do {
               x = rng.nextInt(dimensionX);
               y = rng.nextInt(dimensionY);
            } while (gameBoard.getTile(x, y) != null);

            mines.add(new int[] {x, y});
            gameBoard.setTile(x, y, new GameTile(x, y, true));
        }

        // Fill board with blank tiles
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                if (gameBoard.getTile(x, y) == null) {
                    gameBoard.setTile(x, y, new GameTile(x, y, false));
                }
            }
        }

        // Then generate the surrounding numbers for each mine tile
        for (int[] mine : mines) {
            for (GameTile surroundingTile : getSurroundingTiles(mine[0], mine[1])) {
                gameBoard.incrementMineCount(surroundingTile.getX(), surroundingTile.getY());
            }
        }
    }

    // True: BOOM, False: Safe
    public static boolean leftClick(int x, int y) {
        GameTile tile = MinesweeperApplication.getGameBoard().getTile(x, y);
        if (tile == null) {
            return false;
        }

        // If left click is on a mine, then trigger game over
        if (tile.isMine() && !tile.isFlagged()) {
            ViewController.setTriggerTile(tile);
            return true;
        }

        recursiveRevealTiles(x, y);
        return false;
    }

    // True: BOOM, False: Safe
    public static boolean middleClick(int x, int y) {
        GameBoard gameBoard = MinesweeperApplication.getGameBoard();

        ArrayList<GameTile> surroundingTiles = getSurroundingTiles(x, y);

        int correctFlagCount = gameBoard.getTile(x, y).getSurroundingMines();
        int flagCount = 0;

        // Check that middle click is valid (the correct number of flags are set in the surrounding tiles)
        for (GameTile surroundingTile : surroundingTiles) {
            if (gameBoard.isTileFlagged(surroundingTile.getX(), surroundingTile.getY())) {
                flagCount++;
            }
        }

        // Middle click validated. Now call left click on all surrounding tiles and trigger game over if needed
        if (correctFlagCount == flagCount) {
            boolean isBoom = false;

            // If one of them returns true, then the rest is skipped (short-circuited)
            for (GameTile surroundingTile : surroundingTiles) {
                isBoom = isBoom || leftClick(surroundingTile.getX(), surroundingTile.getY());
            }

            return isBoom;
        }

        return false;
    }

    private static void recursiveRevealTiles(int x, int y) {
        GameTile rootTile = MinesweeperApplication.getGameBoard().getTile(x, y);
        if (rootTile == null) {
            return;
        }

        // Make sure the tile has the right conditions to be revealed
        if (!rootTile.isMine() && rootTile.isHidden() && !rootTile.isFlagged()) {
            rootTile.setVisible();

            // If you clicked on an empty tile (0), then recursively reveal adjacent tiles
            if (rootTile.getSurroundingMines() == 0) {
                for (GameTile surroundingTile : getSurroundingTiles(x, y)) {
                    recursiveRevealTiles(surroundingTile.getX(), surroundingTile.getY());
                }
            }
        }
    }

    // Helper function to return the surrounding tiles of a given tile
    private static ArrayList<GameTile> getSurroundingTiles(int x, int y) {
        GameBoard gameBoard = MinesweeperApplication.getGameBoard();

        ArrayList<GameTile> surroundingTiles = new ArrayList<>();
        GameTile[] tiles = new GameTile[8];
        // Surrounding 8 tiles
        // x-1/y-1, x/y-1, x+1/y-1
        // x-1/y,   x/y,   x+1/y
        // x-1/y+1, x/y+1, x+1/y+1

        // Left column (x - 1)
        tiles[0] = gameBoard.getTile(x-1, y-1);
        tiles[1] = gameBoard.getTile(x-1, y);
        tiles[2] = gameBoard.getTile(x-1, y+1);

        // Center column (x)
        tiles[3] = gameBoard.getTile(x, y-1);
        tiles[4] = gameBoard.getTile(x, y+1);

        // Right column (x + 1)
        tiles[5] = gameBoard.getTile(x+1, y-1);
        tiles[6] = gameBoard.getTile(x+1, y);
        tiles[7] = gameBoard.getTile(x+1, y+1);

        for (GameTile tile : tiles) {
            if (tile != null) {
                surroundingTiles.add(tile);
            }
        }

        return surroundingTiles;
    }
}
