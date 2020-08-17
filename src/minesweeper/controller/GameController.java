package minesweeper.controller;

import minesweeper.model.GameBoard;
import minesweeper.model.GameTile;

import java.util.Random;

public class GameController {

    public static GameBoard generateBoard(int dimensionX, int dimensionY, int mineCount) {
        GameBoard board = new GameBoard(dimensionX, dimensionY);

        // First generate the mines
        Random rng = new Random();
        for (int i = 0; i < mineCount; i++) {
            int x, y;

            do {
               x = rng.nextInt(dimensionX);
               y = rng.nextInt(dimensionY);
            } while (board.getTile(x, y) != null);

            GameTile tile = new GameTile(x, y, true);
            board.setTile(x, y, tile);
        }
        
        // Then generate the surrounding numbers
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                if (board.getTile(x, y) == null) {
                    // Make a new one and check surrounding (8 tiles)
                    // x-1/y-1, x/y-1, x+1/y-1
                    // x-1/y,   x/y,   x+1/y
                    // x-1/y+1, x/y+1, x+1/y+1
                    int surroundingMineCount = 0;
                    GameTile[] surroundingMines = new GameTile[8];
                    surroundingMines[0] = board.getTile(x, y-1);
                    surroundingMines[1] = board.getTile(x+1, y-1);
                    surroundingMines[2] = board.getTile(x+1, y);
                    surroundingMines[3] = board.getTile(x+1, y+1);
                    surroundingMines[4] = board.getTile(x, y+1);
                    surroundingMines[5] = board.getTile(x-1, y+1);
                    surroundingMines[6] = board.getTile(x-1, y);
                    surroundingMines[7] = board.getTile(x-1, y-1);

                    for (GameTile surroundingMine : surroundingMines) {
                        if (surroundingMine != null && surroundingMine.isMine()) {
                            surroundingMineCount++;
                        }
                    }

                    GameTile tile = new GameTile(x, y, surroundingMineCount);
                    board.setTile(x, y, tile);
                }
            }
        }

        return board;
    }
}
