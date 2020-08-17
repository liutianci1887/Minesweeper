package minesweeper.application;

import minesweeper.controller.GameController;
import minesweeper.model.GameBoard;

public class MinesweeperApplication {

    public static void main(String[] args) {
        System.out.println("Minesweeper v0.1");

        GameBoard gameBoard = GameController.generateBoard(5, 5, 1);

        System.out.println(gameBoard);
    }

}
