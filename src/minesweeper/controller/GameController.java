package minesweeper.controller;

import minesweeper.model.GameBoard;
import minesweeper.model.GameTile;

import java.util.ArrayList;
import java.util.Random;

public class GameController {

    public static GameBoard generateBoard(int dimensionX, int dimensionY, int mineCount) {
        GameBoard board = new GameBoard(dimensionX, dimensionY);

        // First generate the mines
        Random rng = new Random();
        ArrayList<int[]> mines = new ArrayList<>();
        for (int i = 0; i < mineCount; i++) {
            int x, y;

            do {
               x = rng.nextInt(dimensionX);
               y = rng.nextInt(dimensionY);
            } while (board.getTile(x, y) != null);

            mines.add(new int[] {x, y});
            board.setTile(x, y, new GameTile(x, y, true));
        }

        // Fill board with blank tiles
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                if (board.getTile(x, y) == null) {
                    board.setTile(x, y, new GameTile(x, y, false));
                }
            }
        }

        // Then generate the surrounding numbers
        for (int[] mine : mines) {
            // Check surrounding (8 tiles)
            // x-1/y-1, x/y-1, x+1/y-1
            // x-1/y,   x/y,   x+1/y
            // x-1/y+1, x/y+1, x+1/y+1
            board.incrementMineCount(mine[0], mine[1]-1);
            board.incrementMineCount(mine[0], mine[1]+1);
            board.incrementMineCount(mine[0]+1, mine[1]-1);
            board.incrementMineCount(mine[0]+1, mine[1]);
            board.incrementMineCount(mine[0]+1, mine[1]+1);
            board.incrementMineCount(mine[0]-1, mine[1]-1);
            board.incrementMineCount(mine[0]-1, mine[1]);
            board.incrementMineCount(mine[0]-1, mine[1]+1);
        }

        return board;
    }
}
