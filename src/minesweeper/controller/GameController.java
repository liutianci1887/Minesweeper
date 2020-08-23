package minesweeper.controller;

import minesweeper.application.MinesweeperApplication;
import minesweeper.model.GameBoard;
import minesweeper.model.GameTile;

import java.util.ArrayList;
import java.util.Random;

public class GameController {

    public static void newBoard(int dimensionX, int dimensionY, int mineCount) {
        GameBoard gameBoard = new GameBoard(dimensionX, dimensionY);

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

        // Then generate the surrounding numbers
        for (int[] mine : mines) {
            // Surrounding 8 tiles
            // x-1/y-1, x/y-1, x+1/y-1
            // x-1/y,   x/y,   x+1/y
            // x-1/y+1, x/y+1, x+1/y+1

            // Left column (x - 1)
            gameBoard.incrementMineCount(mine[0]-1, mine[1]-1);
            gameBoard.incrementMineCount(mine[0]-1, mine[1]);
            gameBoard.incrementMineCount(mine[0]-1, mine[1]+1);

            // Center column (x)
            gameBoard.incrementMineCount(mine[0], mine[1]-1);
            gameBoard.incrementMineCount(mine[0], mine[1]+1);

            // Right column (x + 1)
            gameBoard.incrementMineCount(mine[0]+1, mine[1]-1);
            gameBoard.incrementMineCount(mine[0]+1, mine[1]);
            gameBoard.incrementMineCount(mine[0]+1, mine[1]+1);
        }

        MinesweeperApplication.setGameBoard(gameBoard);
    }

    // True: BOOM, False: Safe
    public static boolean revealTile(int x, int y) {
        GameTile tile = MinesweeperApplication.getGameBoard().getTile(x, y);

        tile.setVisible();
        return tile.isMine();
    }
}
